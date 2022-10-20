package team20.issuetracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.issue.Issue;
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
import team20.issuetracker.service.dto.response.ResponseIssueDto;
import team20.issuetracker.service.dto.response.ResponseReadAllIssueDto;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final MilestoneRepository milestoneRepository;
    private final AssigneeRepository assigneeRepository;
    private final LabelRepository labelRepository;
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
    public ResponseReadAllIssueDto findAllIssuesByCondition(String condition, PageRequest pageRequest) {
        Map<String, String> conditionMap = new HashMap<>();
        String[] conditionSplit = condition.split(", ");

        for (int i = 0; i < conditionSplit.length; i++) {
            String key = conditionSplit[i].split(":")[0];
            String value = conditionSplit[i].split(":")[1];
            conditionMap.put(key, value);
        }

        Page<Issue> allIssuesByCondition = issueRepository.findAllIssuesByCondition(conditionMap, pageRequest);
        Page<ResponseIssueDto> responseIssueDtos = allIssuesByCondition.map(ResponseIssueDto::of);
        Map<IssueStatus, Long> count = countByIssueStatusCheck(allIssuesByCondition, IssueStatus.valueOf(conditionMap.get("is").toUpperCase()));

        return ResponseReadAllIssueDto.of(count.get(IssueStatus.OPEN),count.get(IssueStatus.CLOSED), responseIssueDtos);
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

    private Issue associationMethodCall(List<Assignee> assignees, List<Label> labels, Issue newIssue) {
        Issue savedIssue = issueRepository.save(newIssue);
        Member findMember = memberRepository.findByOauthId(savedIssue.getAuthorId())
                .orElseThrow(() -> new CheckEntityException("해당 Member 는 존재하지 않습니다", HttpStatus.BAD_REQUEST));

        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        savedIssue.addMember(findMember);
        return savedIssue;
    }
}
