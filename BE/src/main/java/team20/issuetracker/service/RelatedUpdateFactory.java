package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.exception.CheckEntityException;

public interface RelatedUpdateFactory {
    RelatedUpdatable getRelatedType(UpdateType updateType);
}

@RequiredArgsConstructor
class SimpleRelatedUpdateFactory implements RelatedUpdateFactory {

    private final MilestoneRepository milestoneRepository;
    private final LabelRepository labelRepository;
    private final AssigneeRepository assigneeRepository;

    @Override
    public RelatedUpdatable getRelatedType(UpdateType updateType) {
        if (updateType.equals(UpdateType.MILESTONE)) {
            return new RelatedMilestone(milestoneRepository);

        } else if (updateType.equals(UpdateType.LABELS)) {
            return new RelatedLabel(labelRepository);

        } else if (updateType.equals(UpdateType.ASSIGNEES)) {
            return new RelatedAssignee(assigneeRepository);

        } else {
            throw new CheckEntityException("해당 UpdateType 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
