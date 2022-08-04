package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.comment.CommentRepository;
import team20.issuetracker.domain.issue.*;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleDto;
import team20.issuetracker.service.dto.response.ResponseIssueDto;
import team20.issuetracker.service.dto.response.ResponseReadAllIssueDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final MilestoneRepository milestoneRepository;
    private final AssigneeRepository assigneeRepository;
    private final LabelRepository labelRepository;
    private final CommentRepository commentRepository;
    private final IssueLabelRepository issueLabelRepository;
    private final IssueAssigneeRepository issueAssigneeRepository;

    @Transactional
    public Long save(RequestSaveIssueDto requestSaveIssueDto) {

        String title = requestSaveIssueDto.getTitle();
        String content = requestSaveIssueDto.getContent() == null ? "" : requestSaveIssueDto.getContent();

        List<Assignee> assignees = assigneeRepository.findAllById(requestSaveIssueDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestSaveIssueDto.getLabelIds());
        Milestone milestone = null;
        if (requestSaveIssueDto.getMilestoneId() != null) {
            milestone = milestoneRepository.findById(requestSaveIssueDto.getMilestoneId())
                    .orElseThrow(() -> new NoSuchElementException("해당 Milestone 은 존재하지 않습니다."));
        }

        // TODO : Issue 엔티티 만들어서 값 넣어주기.
        Issue newIssue = Issue.of(title, content, milestone);

        // TODO : 연관관계 편의 메서드를 통해 빈 값 채워주기
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);

        return issueRepository.save(newIssue).getId();
    }

    // TODO 모든 이슈 조회
    @Transactional(readOnly = true)
    public List<ResponseIssueDto> findAll(String oauthId) {
        List<Issue> findIssues = issueRepository.findAllMyIssues(oauthId);

        return findIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());
    }

    // TODO Open 되어 있는 모든 이슈 조회
    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllOpenIssue(String oauthId) {
        List<Issue> findOpenIssues = issueRepository.findAllMyIssues(oauthId).stream()
                .filter(issue -> issue.getStatus().equals(IssueStatus.OPEN))
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findOpenIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        Long openIssueCount = issueRepository.countTest(oauthId, IssueStatus.OPEN);
        Long closeIssueCount = issueRepository.countTest(oauthId, IssueStatus.CLOSE);
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    // TODO Close 되어 있는 모든 이슈 조회
    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllCloseIssue(String oauthId) {
        List<Issue> findCloseIssues = issueRepository.findAllMyIssues(oauthId).stream()
                .filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE))
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findCloseIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        Long openIssueCount = issueRepository.countTest(oauthId, IssueStatus.OPEN);
        Long closeIssueCount = issueRepository.countTest(oauthId, IssueStatus.CLOSE);
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    // --------- 아래부터 싹 다시 수정

    // TODO 내가 할당된 모든 이슈 조회 (여기 count 가 좀 이상함 / 이런식으로 할꺼면 갯수 세는 issueRepository 새로 만들고 인자에 oauthId 뺴야함)
    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAssigneeByMeIssues(String oauthId) {
        List<IssueAssignee> findIssueAssignees = issueAssigneeRepository.findAllAssignees().stream()
                .filter(issueAssignee -> issueAssignee.getAssignee().getAuthorId().equals(oauthId))
                .collect(Collectors.toList());

        List<Issue> findIssues = findIssueAssignees.stream()
                .map(IssueAssignee::getIssue)
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        Long openIssueCount = issueRepository.countTest(oauthId, IssueStatus.OPEN);
        Long closeIssueCount = issueRepository.countTest(oauthId, IssueStatus.CLOSE);
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    // TODO 내가 할당됐고 모든 열린 또는 닫힌 이슈 조회
    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAssigneeByMeStatusIssues(String oauthId, String issueStatus) {
        List<IssueAssignee> findIssueAssignees = issueAssigneeRepository.findAllAssignees().stream()
                .filter(issueAssignee -> issueAssignee.getAssignee().getAuthorId().equals(oauthId))
                .filter(issueAssignee -> issueAssignee.getIssue().getStatus().toString().equals(issueStatus.toUpperCase()))
                .collect(Collectors.toList());

        List<Issue> findIssues = findIssueAssignees.stream()
                .map(IssueAssignee::getIssue)
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        Long openIssueCount = issueRepository.countTest(oauthId, IssueStatus.OPEN);
        Long closeIssueCount = issueRepository.countTest(oauthId, IssueStatus.CLOSE);
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    // TODO 내가 작성한 모든 이슈 조회
    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllMyIssues(String oauthId) {
        List<Issue> findAllMyIssues = issueRepository.findAllMyIssues(oauthId);

        List<ResponseIssueDto> responseIssueDtos = findAllMyIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        Long openIssueCount = issueRepository.countTest(oauthId, IssueStatus.OPEN);
        Long closeIssueCount = issueRepository.countTest(oauthId, IssueStatus.CLOSE);
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    // TODO 내가 작성한 모든 열린 또는 닫힌 이슈 조회
    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllMyStatusIssues(String oauthId, String issueStatus) {
        List<Issue> findAllMyIssues = issueRepository.findAllMyIssues(oauthId).stream()
                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findAllMyIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        Long openIssueCount = issueRepository.countTest(oauthId, IssueStatus.OPEN);
        Long closeIssueCount = issueRepository.countTest(oauthId, IssueStatus.CLOSE);
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    // TODO 특정 검색어에 포함되는 모든 이슈 조회
    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllSearchIssues(String oauthId, String title) {
        List<Issue> findSearchIssues = issueRepository.findAllSearchIssues(oauthId, title);

        List<ResponseIssueDto> responseIssueDtos = findSearchIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        Long openIssueCount = issueRepository.countTest(oauthId, IssueStatus.OPEN);
        Long closeIssueCount = issueRepository.countTest(oauthId, IssueStatus.CLOSE);
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    // TODO 특정 Title 에 해당하며 열린 또는 닫힌 모든 이슈 검색
    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllSearchStatusIssues(String oauthId, String title, String issueStatus) {
        List<Issue> findSearchStatusIssues = issueRepository.findAllSearchIssues(oauthId, title).stream()
                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findSearchStatusIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        Long openIssueCount = issueRepository.countTest(oauthId, IssueStatus.OPEN);
        Long closeIssueCount = issueRepository.countTest(oauthId, IssueStatus.CLOSE);
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    // TODO 특정 이슈 상세 조회
    @Transactional(readOnly = true)
    public ResponseIssueDto detail(Long id) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Issue 는 존재하지 않습니다"));

        return ResponseIssueDto.from(findIssue);
    }

    // TODO 특정 이슈 삭제
    @Transactional
    public void delete(Long id) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Issue 는 존재하지 않습니다."));

        issueRepository.delete(findIssue);
    }

    // TODO 특정 이슈 제목 수정
    @Transactional
    public Long updateTitle(Long id, RequestUpdateIssueTitleDto requestUpdateIssueTitleDto) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Issue 는 존재하지 않습니다."));

        findIssue.updateTitle(requestUpdateIssueTitleDto);

        return findIssue.getId();
    }

    // TODO 내가 댓글을 단 이슈 모든 이슈 조회
//    @Transactional(readOnly = true)
//    public List<ResponseIssueDto> findCommentByMeIssues(String oauthId) {
//
//        // 내가 작성한 댓글 모두 조회
//        List<Comment> comments = commentRepository.findAll().stream()
//                .filter(comment -> comment.getAuthor().equals(oauthId))
//                .collect(Collectors.toList());
//
//        // 댓글 쪽에서 이슈를 가지고 이슈 리스트 뽑아오기
//        List<Issue> findIssues = comments.stream()
//                .map(Comment::getIssue)
//                .collect(Collectors.toList());

//        issueLabelRepository

//        issueLabelRepository.
//        List<IssueAssignee> issueAssignees = issueAssigneeRepository.findAll();


//        return null;
//    }

    // TODO 319 라인부터 아래로 곂치는 메서드 리팩토링 필요
    /*
        170 ~ 176 Line - 모든 이슈 Response
        178 ~ 184 Line - 모든 열린 이슈 Response
        186 ~ 192 Line - 모든 닫힌 이슈 Response
        201 ~ 206 Line - 검색된 키워드의 전체 이슈
     */
    public ResponseReadAllIssueDto getAllIssueData(List<ResponseIssueDto> issues) {
        long openIssueCount = issues.stream().filter(issue -> issue.getIssueStatus().equals(IssueStatus.OPEN)).count();
        long closedIssueCount = issues.stream().filter(issue -> issue.getIssueStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = issues.stream().map(issue -> issue.getLabels().size()).count();

        return ResponseReadAllIssueDto.of(openIssueCount, closedIssueCount, labelCount, issues);
    }
}
