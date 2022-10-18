package team20.issuetracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.comment.CommentRepository;
import team20.issuetracker.domain.issue.Issue;
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
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleWithContentDto;
import team20.issuetracker.service.dto.request.RequestUpdateManyIssueStatus;
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

        if (!requestSaveIssueDto.getMilestoneIds().isEmpty()) {
            Milestone milestone = milestoneRepository.getReferenceById(requestSaveIssueDto.getMilestoneIds().get(0));
            Issue newIssue = Issue.of(title, content, milestone);
            milestone.updateIssue(newIssue);
            Issue savedIssue = associationMethodCall(assignees, labels, newIssue);
            return savedIssue.getId();
        }

        Issue newIssue = Issue.of(title, content, null);
        Issue savedIssue = associationMethodCall(assignees, labels, newIssue);
        return savedIssue.getId();

    }

    @Transactional(readOnly = true)
    public ResponseIssueDto detail(Long id) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다", HttpStatus.BAD_REQUEST));

        return ResponseIssueDto.of(findIssue);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllOpenAndCloseIssues(PageRequest pageRequest, IssueStatus issueStatus) {
        Page<Issue> findIssuesByStatus = issueRepository.findIssues(pageRequest, issueStatus);
        Map<IssueStatus, Long> countByIssueStatusMap = countByIssueStatusCheck(findIssuesByStatus, issueStatus);
        return getResponseReadAllIssueDto(findIssuesByStatus, countByIssueStatusMap);
    }

    private Map<IssueStatus, Long> countByIssueStatusCheck(Page<Issue> findIssueByStatus, IssueStatus issueStatus) {
        EnumMap<IssueStatus, Long> countByIssueStatusMap = new EnumMap<>(IssueStatus.class);
        long totalIssueCount = issueRepository.findByIssueCount();

        if (issueStatus.equals(IssueStatus.OPEN)) {
            countByIssueStatusMap.put(IssueStatus.OPEN, findIssueByStatus.getTotalElements());
            countByIssueStatusMap.put(IssueStatus.CLOSED, totalIssueCount - findIssueByStatus.getTotalElements());
            return countByIssueStatusMap;
        }

        countByIssueStatusMap.put(IssueStatus.OPEN, totalIssueCount - findIssueByStatus.getTotalElements());
        countByIssueStatusMap.put(IssueStatus.CLOSED, findIssueByStatus.getTotalElements());
        return countByIssueStatusMap;
    }

    @Transactional
    public void delete(Long id) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        issueRepository.delete(findIssue);
    }

    @Transactional
    public Long updateTitleWithContent(Long id, RequestUpdateIssueTitleWithContentDto requestUpdateIssueTitleWithContentDto) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        findIssue.updateTitleWithContent(requestUpdateIssueTitleWithContentDto);
        return findIssue.getId();
    }

    @Transactional
    public Long updateIssueRelated(Long id, String type, @Valid RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto) {
        UpdateType updateType = UpdateType.valueOf(type.toUpperCase());
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        switch (updateType) {
            case MILESTONE:
                Milestone findMilestone = milestoneRepository.findById(requestUpdateIssueRelatedDto.getId())
                        .orElseThrow(() -> new CheckEntityException("해당 Milestone 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
                findIssue.updateMilestone(findMilestone);
                break;
            case LABELS:
                Label findLabel = labelRepository.findById(requestUpdateIssueRelatedDto.getId())
                        .orElseThrow(() -> new CheckEntityException("해당 Label 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
                findIssue.updateLabels(findLabel);
                break;
            case ASSIGNEES:
                Assignee findAssignee = assigneeRepository.findById(requestUpdateIssueRelatedDto.getId())
                        .orElseThrow(() -> new CheckEntityException("해당 Assignee 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
                findIssue.updateAssignees(findAssignee);
                break;
            default:
                throw new CheckEntityException("해당 UpdateType 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        return findIssue.getId();
    }

    @Transactional
    public void updateManyIssueStatus(RequestUpdateManyIssueStatus requestUpdateManyIssueStatus) {
        List<Long> issueIds = requestUpdateManyIssueStatus.getIds();
        IssueStatus status = requestUpdateManyIssueStatus.getIssueStatus();
        issueRepository.updateManyIssueStatus(issueIds, status);
    }

    private Issue associationMethodCall(List<Assignee> assignees, List<Label> labels, Issue newIssue) {
        Issue savedIssue = issueRepository.save(newIssue);
        Member findMember = memberRepository.findByOauthId(savedIssue.getAuthorId())
                .orElseThrow(() -> new CheckEntityException("해당 Member 는 존재하지 않습니다", HttpStatus.BAD_REQUEST));

        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        savedIssue.addMember(findMember);
        return savedIssue;
    }

    private ResponseReadAllIssueDto getResponseReadAllIssueDto(Page<Issue> findAllIssueByIssueStatus, Map<IssueStatus, Long> countByIssueStatusMap) {
        Page<ResponseIssueDto> responseIssueDtos = responseIssueDtos(findAllIssueByIssueStatus);
        return ResponseReadAllIssueDto.of(countByIssueStatusMap.get(IssueStatus.OPEN), countByIssueStatusMap.get(IssueStatus.CLOSED), responseIssueDtos);
    }

    private Page<ResponseIssueDto> responseIssueDtos(Page<Issue> findIssues) {
        return findIssues.map(ResponseIssueDto::of);
    }

//    private List<Issue> filterIssueStatus(List<Issue> findIssues, String issueStatus) {
//        return findIssues.stream()
//                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
//                .collect(Collectors.toList());
//    }

//    @Transactional(readOnly = true)
//    public ResponseReadAllIssueDto findAssigneeByMeIssues(String oauthId) {
//        List<IssueAssignee> findIssueAssignees = issueAssigneeRepository.findAllAssignees().stream()
//                .filter(issueAssignee -> issueAssignee.getAssignee().getAuthorId().equals(oauthId))
//                .collect(Collectors.toList());
//
//        List<Issue> findIssues = findIssueAssignees.stream()
//                .map(IssueAssignee::getIssue)
//                .collect(Collectors.toList());
//
//        return getResponseReadAllIssueDto(findIssues);
//    }

//    @Transactional(readOnly = true)
//    public ResponseReadAllIssueDto findAssigneeByMeStatusIssues(String oauthId, String issueStatus) {
//        List<IssueAssignee> findIssueAssignees = issueAssigneeRepository.findAllAssignees().stream()
//                .filter(issueAssignee -> issueAssignee.getAssignee().getAuthorId().equals(oauthId))
//                .collect(Collectors.toList());
//
//        List<Issue> findIssues = findIssueAssignees.stream()
//                .map(IssueAssignee::getIssue)
//                .collect(Collectors.toList());
//
//        List<Issue> findIssueByIssueStatus = filterIssueStatus(findIssues, issueStatus);
//
//        return getResponseReadAllIssueDto(findIssueByIssueStatus, findIssues);
//    }

//    @Transactional(readOnly = true)
//    public ResponseReadAllIssueDto findAllMyIssues(String oauthId) {
//        List<Issue> findAllMyIssues = issueRepository.findAllMyIssues(oauthId);
//
//        return getResponseReadAllIssueDto(findAllMyIssues);
//    }

//    @Transactional(readOnly = true)
//    public ResponseReadAllIssueDto findAllMyStatusIssues(String oauthId, String issueStatus) {
//        List<Issue> findAllMyIssues = issueRepository.findAllMyIssues(oauthId);
//        List<Issue> findAllMyIssueByIssueStatus = filterIssueStatus(findAllMyIssues, issueStatus);
//
//        return getResponseReadAllIssueDto(findAllMyIssueByIssueStatus, findAllMyIssues);
//    }

//    @Transactional(readOnly = true)
//    public ResponseReadAllIssueDto findAllSearchIssues(String title) {
//        List<Issue> findSearchIssues = issueRepository.findAllIssuesByTitle(title);
//
//        return getResponseReadAllIssueDto(findSearchIssues);
//    }

//    @Transactional(readOnly = true)
//    public ResponseReadAllIssueDto findAllSearchStatusIssues(String title, String issueStatus) {
//        List<Issue> findSearchIssues = issueRepository.findAllIssuesByTitle(title);
//        List<Issue> findAllIssuesByStatus = findSearchIssues.stream()
//                .filter(issue -> issue.getStatus().toString().equals(issueStatus.toUpperCase()))
//                .collect(Collectors.toList());
//
//        return getResponseReadAllIssueDto(findAllIssuesByStatus, findSearchIssues);
//    }

//    @Transactional(readOnly = true)
//    public ResponseReadAllIssueDto filterCommentByMeIssue(String oauthId) {
//        List<Comment> comments = commentRepository.findAll().stream()
//                .filter(comment -> comment.getAuthorId().equals(oauthId))
//                .collect(Collectors.toList());
//
//        List<Issue> findAllIssuesByMyComment = comments.stream()
//                .map(Comment::getIssue)
//                .distinct()
//                .collect(Collectors.toList());
//
//        return getResponseReadAllIssueDto(findAllIssuesByMyComment);
//    }

//    @Transactional(readOnly = true)
//    public ResponseReadAllIssueDto filterCommentByMeStatusIssue(String oauthId, String issueStatus) {
//        List<Comment> comments = commentRepository.findAll().stream()
//                .filter(comment -> comment.getAuthorId().equals(oauthId))
//                .collect(Collectors.toList());
//
//        List<Issue> findIssues = comments.stream()
//                .map(Comment::getIssue)
//                .distinct()
//                .collect(Collectors.toList());
//
//        List<Issue> findIssueByIssueStatus = filterIssueStatus(findIssues, issueStatus);
//
//        return getResponseReadAllIssueDto(findIssueByIssueStatus, findIssues);
//    }

//    private ResponseReadAllIssueDto getResponseReadAllIssueDto(List<Issue> findAllIssues) {
//        List<ResponseIssueDto> responseIssueDtos = responseIssueDtos(findAllIssues);
//
//        long openIssueCount = getOpenIssuesCountByFindAll(findAllIssues);
//        long closeIssueCount = getCloseIssuesCountByFindAll(findAllIssues);
//
//        return ResponseReadAllIssueDto.of(openIssueCount, closeIssueCount, responseIssueDtos);
//    }
}
