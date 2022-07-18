package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.issue.SaveIssueDto;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final MilestoneRepository milestoneRepository;
    private final AssigneeRepository assigneeRepository;
    private final LabelRepository labelRepository;

    @Transactional
    public Long save(SaveIssueDto saveIssueDto) {

        String author = saveIssueDto.getAuthor();
        String title = saveIssueDto.getTitle();
        String content = saveIssueDto.getContent() == null ? "" : saveIssueDto.getContent();
        LocalDateTime createdAt = saveIssueDto.getCreatedAt();

        List<Assignee> assignees = assigneeRepository.findAllById(saveIssueDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(saveIssueDto.getLabelIds());
        Milestone milestone = null;
        if(saveIssueDto.getMilestoneId() != null) {
            milestone = milestoneRepository.findById(saveIssueDto.getMilestoneId())
                    .orElseThrow(() -> new NoSuchElementException("해당 Milestone 은 존재하지 않습니다."));
        }

        // TODO : Issue 엔티티 만들어서 값 넣어주기.
        Issue newIssue = Issue.of(author, title, content, createdAt, milestone);

        // TODO : 연관관계 편의 메서드를 통해 빈 값 채워주기
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);

        return issueRepository.save(newIssue).getId();
    }
}
