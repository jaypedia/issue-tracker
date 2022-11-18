package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.exception.CheckUpdateTypeException;

@RequiredArgsConstructor
public class SimpleRelatedUpdateFactory implements RelatedUpdateFactory {

    private final MilestoneRepository milestoneRepository;
    private final LabelRepository labelRepository;
    private final AssigneeRepository assigneeRepository;

    @Override
    public RelatedUpdatable getRelatedType(UpdateType updateType) {
        switch(updateType) {
            case MILESTONE:
                return new RelatedMilestone(milestoneRepository);

            case LABELS:
                return new RelatedLabel(labelRepository);

            case ASSIGNEES:
                return new RelatedAssignee(assigneeRepository);
        }

        throw new CheckUpdateTypeException("해당 UpdateType 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
