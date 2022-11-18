package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;

@RequiredArgsConstructor
public class RelatedAssignee implements RelatedUpdatable {
    private final AssigneeRepository assigneeRepository;

    @Override
    public void updateRelatedType(RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto, UpdateType updateType, Issue findIssue) {
        Assignee findAssignee = assigneeRepository.findById(requestUpdateIssueRelatedDto.getId())
            .orElseThrow(() -> new CheckEntityException("해당 Assignee 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        findIssue.updateAssignees(findAssignee);
    }
}
