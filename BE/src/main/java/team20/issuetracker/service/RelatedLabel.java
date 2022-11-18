package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;

@RequiredArgsConstructor
public class RelatedLabel implements RelatedUpdatable {
    private final LabelRepository labelRepository;

    @Override
    public void updateRelatedType(RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto, UpdateType updateType, Issue findIssue) {
        Label findLabel = labelRepository.findById(requestUpdateIssueRelatedDto.getId())
            .orElseThrow(() -> new CheckEntityException("해당 Label 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        findIssue.updateLabels(findLabel);
    }
}
