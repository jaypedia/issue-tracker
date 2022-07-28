package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.issue.*;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
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

    @Transactional(readOnly = true)
    public List<ResponseIssueDto> findAll() {
        List<Issue> findIssues = issueRepository.findAll();
        List<IssueLabel> issueLabels = issueLabelRepository.findAllTest();
        List<IssueAssignee> issueAssignees = issueAssigneeRepository.findAllTest();

        Set<Label> labels = issueLabels.stream()
                .map(IssueLabel::getLabel)
                .collect(Collectors.toSet());

        Set<Assignee> assignees = issueAssignees.stream()
                .map(IssueAssignee::getAssignee)
                .collect(Collectors.toSet());

        return findIssues.stream()
                .map(issue -> ResponseIssueDto.of(issue, labels, assignees))
                .collect(Collectors.toList());
    }

    public ResponseReadAllIssueDto getAllIssueData(List<ResponseIssueDto> issues) {
        long openIssueCount = issues.stream().filter(issue -> issue.getIssueStatus().equals(IssueStatus.OPEN)).count();
        long closedIssueCount = issues.stream().filter(issue -> issue.getIssueStatus().equals(IssueStatus.CLOSE)).count();
        long labelCount = issues.stream().map(issue -> issue.getLabels().size()).count();

        return ResponseReadAllIssueDto.of(openIssueCount, closedIssueCount, labelCount, issues);
    }

    @Transactional(readOnly = true)
    public ResponseIssueDto detail(String oauthId, Long id) {
        Issue findIssue = issueRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Issue 는 존재하지 않습니다"));

        List<IssueLabel> issueLabels = issueLabelRepository.findAllById(id);
        List<IssueAssignee> issueAssignees = issueAssigneeRepository.findAllById(id);

        Set<Label> labels = issueLabels.stream()
                .map(IssueLabel::getLabel)
                .collect(Collectors.toSet());

        Set<Assignee> assignees = issueAssignees.stream()
                .map(IssueAssignee::getAssignee)
                .collect(Collectors.toSet());

        return ResponseIssueDto.of(findIssue, labels, assignees);
    }
}
