package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.domain.milestone.MilestoneStatus;
import team20.issuetracker.domain.milestone.request.SaveMilestoneDto;
import team20.issuetracker.domain.milestone.response.MilestoneDto;
import team20.issuetracker.domain.milestone.response.ResponseMilestone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
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
    public List<MilestoneDto> findAll() {
        List<Milestone> findMilestones = milestoneRepository.findAll();

        return findMilestones.stream()
                .map(MilestoneDto::of).collect(Collectors.toList());
    }

    public ResponseMilestone getAllMilestoneData(List<MilestoneDto> milestones) {
        int allMilestoneCount = milestones.size();
        long openMilestonesCount = milestones.stream().filter(milestone -> milestone.getMilestoneStatus().equals(MilestoneStatus.OPEN)).count();
        long closeMilestonesCount = milestones.stream().filter(milestone -> milestone.getMilestoneStatus().equals(MilestoneStatus.CLOSE)).count();

        return ResponseMilestone.of(allMilestoneCount, openMilestonesCount, closeMilestonesCount, milestones);
    }

    @Transactional(readOnly = true)
    public void delete(Long id) {
        Milestone findMilestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Milestone 은 존재하지 않습니다."));

        milestoneRepository.delete(findMilestone);
    }
}
