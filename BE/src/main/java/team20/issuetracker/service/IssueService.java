package team20.issuetracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.comment.CommentRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueAssignee;
import team20.issuetracker.domain.issue.IssueAssigneeRepository;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.issue.IssueStatus;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleDto;
import team20.issuetracker.service.dto.response.ResponseIssueDto;
import team20.issuetracker.service.dto.response.ResponseReadAllIssueDto;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final MilestoneRepository milestoneRepository;
    private final AssigneeRepository assigneeRepository;
    private final LabelRepository labelRepository;
    private final CommentRepository commentRepository;
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

        Issue newIssue = Issue.of(title, content, milestone);

        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);

        return issueRepository.save(newIssue).getId();
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAll() {
        List<Issue> findIssues = issueRepository.findAll();

        List<ResponseIssueDto> responseIssueDtos = findIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        long openIssueCount = findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
        long closeIssueCount = findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllOpenIssue() {
        List<Issue> findIssues = issueRepository.findAll();
        List<Issue> findOpenIssues = findIssues.stream()
                .filter(issue -> issue.getStatus().equals(IssueStatus.OPEN))
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findOpenIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        long openIssueCount = findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
        long closeIssueCount = findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllCloseIssue() {
        List<Issue> findIssues = issueRepository.findAll();
        List<Issue> findCloseIssues = findIssues.stream()
                .filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE))
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findCloseIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        long openIssueCount = findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
        long closeIssueCount = findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

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

        long openIssueCount = findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
        long closeIssueCount = findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAssigneeByMeStatusIssues(String oauthId, String issueStatus) {
        List<IssueAssignee> findIssueAssignees = issueAssigneeRepository.findAllAssignees().stream()
                .filter(issueAssignee -> issueAssignee.getAssignee().getAuthorId().equals(oauthId))
                .collect(Collectors.toList());

        List<Issue> findIssues = findIssueAssignees.stream()
                .map(IssueAssignee::getIssue)
                .collect(Collectors.toList());

        List<Issue> findIssueByIssueStatus = findIssues.stream()
                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findIssueByIssueStatus.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        long openIssueCount = findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
        long closeIssueCount = findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllMyIssues(String oauthId) {
        List<Issue> findAllMyIssues = issueRepository.findAllMyIssues(oauthId);

        List<ResponseIssueDto> responseIssueDtos = findAllMyIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        long openIssueCount = findAllMyIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
        long closeIssueCount = findAllMyIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllMyStatusIssues(String oauthId, String issueStatus) {
        List<Issue> findAllMyIssues = issueRepository.findAllMyIssues(oauthId);
        List<Issue> findAllMyIssueByIssueStatus = findAllMyIssues.stream()
                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findAllMyIssueByIssueStatus.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        long openIssueCount = findAllMyIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
        long closeIssueCount = findAllMyIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllSearchIssues(String title) {
        List<Issue> findSearchIssues = issueRepository.findAllIssuesByTitle(title);

        List<ResponseIssueDto> responseIssueDtos = findSearchIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        long openIssueCount = responseIssueDtos.stream().filter(issue -> issue.getIssueStatus().equals(IssueStatus.OPEN)).count();
        long closeIssueCount = responseIssueDtos.stream().filter(issue -> issue.getIssueStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllSearchStatusIssues(String title, String issueStatus) {
        List<Issue> findAllIssuesByTitle = issueRepository.findAllIssuesByTitle(title);
        List<Issue> findAllIssuesByStatus = findAllIssuesByTitle.stream()
                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
                .collect(Collectors.toList());

        List<ResponseIssueDto> responseIssueDtos = findAllIssuesByStatus.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());

        long openIssueCount = findAllIssuesByTitle.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
        long closeIssueCount = findAllIssuesByTitle.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = labelRepository.findAll().size();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    @Transactional(readOnly = true)
    public ResponseIssueDto detail(Long id) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Issue 는 존재하지 않습니다"));

        return ResponseIssueDto.from(findIssue);
    }

    @Transactional
    public void delete(Long id) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Issue 는 존재하지 않습니다."));

        issueRepository.delete(findIssue);
    }

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
}
