package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.domain.milestone.requestDto.SaveMilestoneDto;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    public final MilestoneRepository milestoneRepository;

    @Transactional
    public Long save(SaveMilestoneDto saveMilestoneDto) {
        String title = saveMilestoneDto.getTitle();
        String description = saveMilestoneDto.getDescription() == null ? "" : saveMilestoneDto.getDescription();
        LocalDateTime startDate = saveMilestoneDto.getStartDate();
        LocalDateTime endDate = saveMilestoneDto.getEndDate();

        Milestone newMilestone = Milestone.of(title, startDate, endDate, description);

        return milestoneRepository.save(newMilestone).getId();
    }
}
