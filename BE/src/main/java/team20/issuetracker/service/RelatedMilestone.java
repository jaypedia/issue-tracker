package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;

@RequiredArgsConstructor
public class RelatedMilestone implements RelatedUpdatable {
    private final MilestoneRepository milestoneRepository;

    @Override
    public void updateRelatedType(RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto, UpdateType updateType, Issue findIssue) {
        Milestone findMilestone = milestoneRepository.findById(requestUpdateIssueRelatedDto.getId())
            .orElseThrow(() -> new CheckEntityException("해당 Milestone 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        findIssue.updateMilestone(findMilestone);
    }
}
