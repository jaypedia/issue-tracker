package team20.issuetracker.service;

import org.apache.coyote.Response;
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

        return getResponseReadAllIssueDto(findIssues);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllOpenIssue() {
        List<Issue> findIssues = issueRepository.findAll();
        List<Issue> findOpenIssues = findIssues.stream()
                .filter(issue -> issue.getStatus().equals(IssueStatus.OPEN))
                .collect(Collectors.toList());

        return getResponseReadAllIssueDto(findOpenIssues, findIssues);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllCloseIssue() {
        List<Issue> findIssues = issueRepository.findAll();
        List<Issue> findCloseIssues = findIssues.stream()
                .filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE))
                .collect(Collectors.toList());

        return getResponseReadAllIssueDto(findCloseIssues, findIssues);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAssigneeByMeIssues(String oauthId) {
        List<IssueAssignee> findIssueAssignees = issueAssigneeRepository.findAllAssignees().stream()
                .filter(issueAssignee -> issueAssignee.getAssignee().getAuthorId().equals(oauthId))
                .collect(Collectors.toList());

        List<Issue> findIssues = findIssueAssignees.stream()
                .map(IssueAssignee::getIssue)
                .collect(Collectors.toList());

        return getResponseReadAllIssueDto(findIssues);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAssigneeByMeStatusIssues(String oauthId, String issueStatus) {
        List<IssueAssignee> findIssueAssignees = issueAssigneeRepository.findAllAssignees().stream()
                .filter(issueAssignee -> issueAssignee.getAssignee().getAuthorId().equals(oauthId))
                .collect(Collectors.toList());

        List<Issue> findIssues = findIssueAssignees.stream()
                .map(IssueAssignee::getIssue)
                .collect(Collectors.toList());

        List<Issue> findIssueByIssueStatus = filterIssueStatus(findIssues, issueStatus);

        return getResponseReadAllIssueDto(findIssueByIssueStatus, findIssues);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllMyIssues(String oauthId) {
        List<Issue> findAllMyIssues = issueRepository.findAllMyIssues(oauthId);

        return getResponseReadAllIssueDto(findAllMyIssues);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllMyStatusIssues(String oauthId, String issueStatus) {
        List<Issue> findAllMyIssues = issueRepository.findAllMyIssues(oauthId);
        List<Issue> findAllMyIssueByIssueStatus = filterIssueStatus(findAllMyIssues, issueStatus);

        return getResponseReadAllIssueDto(findAllMyIssueByIssueStatus, findAllMyIssues);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllSearchIssues(String title) {
        List<Issue> findSearchIssues = issueRepository.findAllIssuesByTitle(title);

        return getResponseReadAllIssueDto(findSearchIssues);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllSearchStatusIssues(String title, String issueStatus) {
        List<Issue> findSearchIssues = issueRepository.findAllIssuesByTitle(title);
        List<Issue> findAllIssuesByStatus = findSearchIssues.stream()
                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
                .collect(Collectors.toList());

        return getResponseReadAllIssueDto(findAllIssuesByStatus, findSearchIssues);
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

    private ResponseReadAllIssueDto getResponseReadAllIssueDto(List<Issue> findAllIssues) {
        List<ResponseIssueDto> responseIssueDtos = responseIssueDtos(findAllIssues);

        long openIssueCount = getOpenIssuesCountByFindAll(findAllIssues);
        long closeIssueCount = getCloseIssuesCountByFindAll(findAllIssues);
        long labelCount = getLabelCount();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    private ResponseReadAllIssueDto getResponseReadAllIssueDto(List<Issue> findAllIssueByIssueStatus, List<Issue> findAllIssues) {
        List<ResponseIssueDto> responseIssueDtos = responseIssueDtos(findAllIssueByIssueStatus);

        long openIssueCount = getOpenIssuesCountByFindAll(findAllIssues);
        long closeIssueCount = getCloseIssuesCountByFindAll(findAllIssues);
        long labelCount = getLabelCount();

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, labelCount, responseIssueDtos);
    }

    private List<ResponseIssueDto> responseIssueDtos(List<Issue> findIssues) {
        return findIssues.stream()
                .map(ResponseIssueDto::from)
                .collect(Collectors.toList());
    }


    private long getOpenIssuesCountByFindAll(List<Issue> findIssues) {
        return findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
    }

    private long getCloseIssuesCountByFindAll(List<Issue> findIssues) {
        return findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSE)).count();
    }

    private long getLabelCount() {
        return labelRepository.findAll().size();
    }

    private List<Issue> filterIssueStatus(List<Issue> findIssues, String issueStatus) {
        return findIssues.stream()
                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
                .collect(Collectors.toList());
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

}
