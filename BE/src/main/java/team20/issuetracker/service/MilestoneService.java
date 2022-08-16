package team20.issuetracker.service;

import org.springframework.http.HttpStatus;
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
import team20.issuetracker.exception.CheckEntityException;
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
        LocalDate dueDate = requestSaveMilestoneDto.getDueDate();

        Milestone newMilestone = Milestone.of(title, dueDate, description);

        return milestoneRepository.save(newMilestone).getId();
    }

    @Transactional(readOnly = true)
    public List<ResponseMilestoneDto> findAll() {
        List<Milestone> findMilestones = milestoneRepository.findAll();

        return findMilestones.stream()
                .map(ResponseMilestoneDto::from).collect(Collectors.toList());
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
                .orElseThrow(() -> new CheckEntityException("해당 Milestone 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        findMilestone.update(requestUpdateMilestoneDto);

        return findMilestone.getId();
    }

    @Transactional(readOnly = true)
    public ResponseMilestoneDto detail(Long id) {
        Milestone findMilestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new CheckEntityException("해당 Milestone 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        return ResponseMilestoneDto.from(findMilestone);
    }

    public ResponseReadAllMilestonesDto getAllMilestoneData(List<ResponseMilestoneDto> milestones) {
        int allMilestoneCount = milestones.size();
        long openMilestonesCount = milestones.stream()
                .filter(milestone -> milestone.getMilestoneStatus().equals(MilestoneStatus.OPEN.toString().toLowerCase()))
                .count();
        long closeMilestonesCount = milestones.stream()
                .filter(milestone -> milestone.getMilestoneStatus().equals(MilestoneStatus.CLOSED.toString().toLowerCase()))
                .count();

        return ResponseReadAllMilestonesDto.of(allMilestoneCount, openMilestonesCount, closeMilestonesCount, milestones);
    }
}
