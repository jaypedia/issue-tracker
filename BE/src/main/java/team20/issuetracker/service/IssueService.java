package team20.issuetracker.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.comment.CommentRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueAssignee;
import team20.issuetracker.domain.issue.IssueAssigneeRepository;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.issue.IssueStatus;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.exception.CheckEntityException;
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
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(RequestSaveIssueDto requestSaveIssueDto) {

        String title = requestSaveIssueDto.getTitle();
        String content = requestSaveIssueDto.getContent() == null ? "" : requestSaveIssueDto.getContent();

        List<Assignee> assignees = assigneeRepository.findAllById(requestSaveIssueDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestSaveIssueDto.getLabelIds());
        Milestone milestone = null;
        if (requestSaveIssueDto.getMilestoneId() != null) {
            milestone = milestoneRepository.findById(requestSaveIssueDto.getMilestoneId())
                    .orElseThrow(() -> new CheckEntityException("해당 Milestone 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
            Issue newIssue = Issue.of(title, content, milestone);
            milestone.updateIssue(newIssue);
            newIssue.addAssignees(assignees);
            newIssue.addLabels(labels);
            return issueRepository.save(newIssue).getId();
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
                .filter(issue -> issue.getStatus().equals(IssueStatus.CLOSED))
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
    public ResponseReadAllIssueDto filterCommentByMeIssue(String oauthId) {
        List<Comment> comments = commentRepository.findAll().stream()
                .filter(comment -> comment.getAuthorId().equals(oauthId))
                .collect(Collectors.toList());

        Set<Issue> findAllIssuesByMyComment = comments.stream()
                .map(Comment::getIssue)
                .collect(Collectors.toSet());

        return getResponseReadAllIssueDto(findAllIssuesByMyComment);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto filterCommentByMeStatusIssue(String oauthId, String issueStatus) {
        List<Comment> comments = commentRepository.findAll().stream()
                .filter(comment -> comment.getAuthorId().equals(oauthId))
                .collect(Collectors.toList());

        Set<Issue> findIssues = comments.stream()
                .map(Comment::getIssue)
                .collect(Collectors.toSet());

        List<Issue> findIssueByIssueStatus = filterIssueStatus(findIssues, issueStatus);

        return getResponseReadAllIssueDto(findIssueByIssueStatus ,findIssues);
    }

    @Transactional(readOnly = true)
    public ResponseIssueDto detail(Long id) {

        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다", HttpStatus.BAD_REQUEST));

        Member findMember = memberRepository.findByOauthId(findIssue.getAuthorId())
                .orElseThrow(() -> new CheckEntityException("해당 Member 는 존재하지 않습니다", HttpStatus.BAD_REQUEST));

        return ResponseIssueDto.of(findIssue, findMember);
    }

    @Transactional
    public void delete(Long id) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        issueRepository.delete(findIssue);
    }

    @Transactional
    public Long updateTitle(Long id, RequestUpdateIssueTitleDto requestUpdateIssueTitleDto) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        findIssue.updateTitle(requestUpdateIssueTitleDto);

        return findIssue.getId();
    }

    private ResponseReadAllIssueDto getResponseReadAllIssueDto(List<Issue> findAllIssues) {
        List<ResponseIssueDto> responseIssueDtos = responseIssueDtos(findAllIssues);

        long openIssueCount = getOpenIssuesCountByFindAll(findAllIssues);
        long closeIssueCount = getCloseIssuesCountByFindAll(findAllIssues);

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, responseIssueDtos);
    }

    private ResponseReadAllIssueDto getResponseReadAllIssueDto(Set<Issue> findAllIssues) {
        List<ResponseIssueDto> responseIssueDtos = responseIssueDtos(findAllIssues);

        long openIssueCount = getOpenIssuesCountByFindAll(findAllIssues);
        long closeIssueCount = getCloseIssuesCountByFindAll(findAllIssues);

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, responseIssueDtos);
    }

    private ResponseReadAllIssueDto getResponseReadAllIssueDto(List<Issue> findAllIssueByIssueStatus, Set<Issue> findAllIssues) {
        List<ResponseIssueDto> responseIssueDtos = responseIssueDtos(findAllIssueByIssueStatus);

        long openIssueCount = getOpenIssuesCountByFindAll(findAllIssues);
        long closeIssueCount = getCloseIssuesCountByFindAll(findAllIssues);

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, responseIssueDtos);
    }

    private ResponseReadAllIssueDto getResponseReadAllIssueDto(List<Issue> findAllIssueByIssueStatus, List<Issue> findAllIssues) {
        List<ResponseIssueDto> responseIssueDtos = responseIssueDtos(findAllIssueByIssueStatus);

        long openIssueCount = getOpenIssuesCountByFindAll(findAllIssues);
        long closeIssueCount = getCloseIssuesCountByFindAll(findAllIssues);

        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, responseIssueDtos);
    }

    private List<ResponseIssueDto> responseIssueDtos(List<Issue> findIssues) {
        return findIssues.stream()
                .map(ResponseIssueDto::of)
                .collect(Collectors.toList());
    }

    private List<ResponseIssueDto> responseIssueDtos(Set<Issue> findIssues) {
        return findIssues.stream()
                .map(ResponseIssueDto::of)
                .collect(Collectors.toList());
    }

    private long getOpenIssuesCountByFindAll(List<Issue> findIssues) {
        return findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
    }

    private long getCloseIssuesCountByFindAll(List<Issue> findIssues) {
        return findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSED)).count();
    }

    private long getOpenIssuesCountByFindAll(Set<Issue> findIssues) {
        return findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count();
    }

    private long getCloseIssuesCountByFindAll(Set<Issue> findIssues) {
        return findIssues.stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSED)).count();
    }

    private List<Issue> filterIssueStatus(List<Issue> findIssues, String issueStatus) {
        return findIssues.stream()
                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
                .collect(Collectors.toList());
    }

    private List<Issue> filterIssueStatus(Set<Issue> findIssues, String issueStatus) {
        return findIssues.stream()
                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
                .collect(Collectors.toList());
    }
}
