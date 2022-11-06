package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;

public interface RelatedUpdatable {
    void updateRelatedType(RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto, UpdateType updateType, Issue findIssue);
}

@RequiredArgsConstructor
class RelatedMilestone implements RelatedUpdatable {
    private final MilestoneRepository milestoneRepository;

    @Override
    public void updateRelatedType(RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto, UpdateType updateType, Issue findIssue) {
        Milestone findMilestone = milestoneRepository.findById(requestUpdateIssueRelatedDto.getId())
            .orElseThrow(() -> new CheckEntityException("해당 Milestone 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        findIssue.updateMilestone(findMilestone);
    }
}

@RequiredArgsConstructor
class RelatedLabel implements RelatedUpdatable {
    private final LabelRepository labelRepository;

    @Override
    public void updateRelatedType(RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto, UpdateType updateType, Issue findIssue) {
        Label findLabel = labelRepository.findById(requestUpdateIssueRelatedDto.getId())
            .orElseThrow(() -> new CheckEntityException("해당 Label 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        findIssue.updateLabels(findLabel);
    }
}

@RequiredArgsConstructor
class RelatedAssignee implements RelatedUpdatable {
    private final AssigneeRepository assigneeRepository;

    @Override
    public void updateRelatedType(RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto, UpdateType updateType, Issue findIssue) {
        Assignee findAssignee = assigneeRepository.findById(requestUpdateIssueRelatedDto.getId())
            .orElseThrow(() -> new CheckEntityException("해당 Assignee 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        findIssue.updateAssignees(findAssignee);
    }
}