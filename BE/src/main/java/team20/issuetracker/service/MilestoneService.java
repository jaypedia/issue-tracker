package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.domain.milestone.MilestoneStatus;
import team20.issuetracker.domain.milestone.request.SaveMilestoneDto;
import team20.issuetracker.domain.milestone.response.ReadMilestoneDto;
import team20.issuetracker.domain.milestone.response.ReadMilestoneListDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<ReadMilestoneDto> findAll() {
        List<Milestone> findMilestones = milestoneRepository.findAll();

        return findMilestones.stream()
                .map(ReadMilestoneDto::of).collect(Collectors.toList());
    }

    public ReadMilestoneListDto getAllMilestoneData(List<ReadMilestoneDto> milestones) {
        int allMilestoneCount = milestones.size();
        long openMilestonesCount = milestones.stream().filter(milestone -> milestone.getMilestoneStatus().equals(MilestoneStatus.OPEN)).count();
        long closeMilestonesCount = milestones.stream().filter(milestone -> milestone.getMilestoneStatus().equals(MilestoneStatus.CLOSE)).count();

        return ReadMilestoneListDto.of(allMilestoneCount, openMilestonesCount, closeMilestonesCount, milestones);
    }
}
