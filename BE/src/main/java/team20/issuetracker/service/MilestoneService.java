package team20.issuetracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.domain.milestone.MilestoneStatus;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseReadAllMilestonesDto;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    public final MilestoneRepository milestoneRepository;

    @Transactional
    public Long save(RequestSaveMilestoneDto requestSaveMilestoneDto) {
        String title = requestSaveMilestoneDto.getTitle();
        String description = requestSaveMilestoneDto.getDescription() == null ? "" : requestSaveMilestoneDto.getDescription();
        LocalDate startDate = requestSaveMilestoneDto.getStartDate();
        LocalDate endDate = requestSaveMilestoneDto.getEndDate();

        Milestone newMilestone = Milestone.of(title, startDate, endDate, description);

        return milestoneRepository.save(newMilestone).getId();
    }

    @Transactional(readOnly = true)
    public List<ResponseMilestoneDto> findAll(String oauthId) {
        List<Milestone> findMilestones = milestoneRepository.findAllByAuthorId(oauthId);

        return findMilestones.stream()
                .map(ResponseMilestoneDto::of).collect(Collectors.toList());
    }

    public ResponseReadAllMilestonesDto getAllMilestoneData(List<ResponseMilestoneDto> milestones) {
        int allMilestoneCount = milestones.size();
        long openMilestonesCount = milestones.stream().filter(milestone -> milestone.getMilestoneStatus().equals(MilestoneStatus.OPEN)).count();
        long closeMilestonesCount = milestones.stream().filter(milestone -> milestone.getMilestoneStatus().equals(MilestoneStatus.CLOSE)).count();

        return ResponseReadAllMilestonesDto.of(allMilestoneCount, openMilestonesCount, closeMilestonesCount, milestones);
    }

    @Transactional
    public void delete(Long id) {
        Milestone findMilestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Milestone 은 존재하지 않습니다."));

        milestoneRepository.delete(findMilestone);
    }

    @Transactional
    public Long update(Long id, RequestUpdateMilestoneDto requestUpdateMilestoneDto) {
        Milestone findMilestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Milestone 은 존재하지 않습니다."));

        findMilestone.update(requestUpdateMilestoneDto);

        return findMilestone.getId();
    }

    public ResponseMilestoneDto detail(Long id) {
        Milestone findMilestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Milestone 은 존재하지 않습니다."));

        return ResponseMilestoneDto.of(findMilestone);
    }
}
