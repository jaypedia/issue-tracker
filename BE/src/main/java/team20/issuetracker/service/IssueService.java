package team20.issuetracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(RequestSaveIssueDto requestSaveIssueDto) {
        String title = requestSaveIssueDto.getTitle();
        String content = requestSaveIssueDto.getContent() == null ? "" : requestSaveIssueDto.getContent();
        List<Assignee> assignees = assigneeRepository.findAllById(requestSaveIssueDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestSaveIssueDto.getLabelIds());

        if (requestSaveIssueDto.getMilestoneIds().isEmpty()) {
            Issue newIssue = Issue.of(title, content, null);
            Issue savedIssue = issueRepository.save(newIssue);
            associationMethodCall(assignees, labels, savedIssue);
            return savedIssue.getId();
        }

        Milestone milestone = milestoneRepository.getReferenceById(requestSaveIssueDto.getMilestoneIds().get(0));
        Issue newIssue = Issue.of(title, content, milestone);
        milestone.updateIssue(newIssue);
        Issue savedIssue = issueRepository.save(newIssue);
        associationMethodCall(assignees, labels, savedIssue);
        return savedIssue.getId();
    }

    private void associationMethodCall(List<Assignee> assignees, List<Label> labels, Issue savedIssue) {
        Member findMember = memberRepository.findByOauthId(savedIssue.getAuthorId())
                .orElseThrow(() -> new CheckEntityException("해당 Member 는 존재하지 않습니다", HttpStatus.BAD_REQUEST));
        savedIssue.addAssignees(assignees);
        savedIssue.addLabels(labels);
        savedIssue.addMember(findMember);
    }

    @Transactional(readOnly = true)
    public ResponseIssueDto detail(Long id) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다", HttpStatus.BAD_REQUEST));

        return ResponseIssueDto.of(findIssue);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllIssueDto findAllIssuesByCondition(String condition, PageRequest pageRequest) {
        MultiValueMap<String, String> conditionMap = new LinkedMultiValueMap<>();
        String[] conditionSplit = condition.split("\\|\\^&");

        for (int i = 0; i < conditionSplit.length; i++) {
            String key = conditionSplit[i].split(":")[0];
            String value = conditionSplit[i].split(":")[1];
            conditionMap.add(key, value);
        }

        Page<Issue> allIssuesByCondition = issueRepository.findAllIssuesByCondition(conditionMap, pageRequest);
        Page<ResponseIssueDto> responseIssueDtos = allIssuesByCondition.map(ResponseIssueDto::of);
        Map<IssueStatus, Long> count = countByIssueCheck(allIssuesByCondition, conditionMap);

        return ResponseReadAllIssueDto.of(count.get(IssueStatus.OPEN), count.get(IssueStatus.CLOSED), responseIssueDtos);
    }

    private Map<IssueStatus, Long> countByIssueCheck(Page<Issue> findIssueByCondition, MultiValueMap<String, String> conditionMap) {
        EnumMap<IssueStatus, Long> countByIssueMap = new EnumMap<>(IssueStatus.class);
        Long allIssueCount = issueRepository.allIssueCountQuery(conditionMap);

        long openIssueCount;
        long closedIssueCount;

        if (conditionMap.get("is").get(0).toUpperCase().equals(IssueStatus.OPEN.name())) {
            openIssueCount = findIssueByCondition.getTotalElements();
            closedIssueCount = allIssueCount - openIssueCount;

            countByIssueMap.put(IssueStatus.OPEN, openIssueCount);
            countByIssueMap.put(IssueStatus.CLOSED, closedIssueCount);

            return countByIssueMap;
        }

        closedIssueCount = findIssueByCondition.getTotalElements();
        openIssueCount = allIssueCount - closedIssueCount;

        countByIssueMap.put(IssueStatus.OPEN, openIssueCount);
        countByIssueMap.put(IssueStatus.CLOSED, closedIssueCount);

        return countByIssueMap;
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
    public Long updateIssueRelated(Long id, String type, RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto) {
        UpdateType updateType = UpdateType.checkUpdateType(type);
        Issue findIssue = issueRepository.findById(id)
            .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        RelatedUpdatable relatedType = checkUpdateType(updateType);
        relatedType.updateRelatedType(requestUpdateIssueRelatedDto, updateType, findIssue);

        return findIssue.getId();
    }

    private RelatedUpdatable checkUpdateType(UpdateType updateType) {
        return new SimpleRelatedUpdateFactory(milestoneRepository, labelRepository, assigneeRepository)
            .getRelatedType(updateType);
    }

    @Transactional
    public void updateManyIssueStatus(RequestUpdateManyIssueStatus requestUpdateManyIssueStatus) {
        List<Long> issueIds = requestUpdateManyIssueStatus.getIds();
        IssueStatus status = requestUpdateManyIssueStatus.getIssueStatus();
        issueRepository.updateManyIssueStatus(issueIds, status);
    }
}
