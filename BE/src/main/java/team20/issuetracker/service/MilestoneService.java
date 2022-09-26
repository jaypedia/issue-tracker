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
    public ResponseReadAllMilestonesDto findAll() {
        List<Milestone> findMilestones = milestoneRepository.findAll();

        return getResponseReadAllMilestoneDto(findMilestones);
    }

    @Transactional(readOnly = true)
    public ResponseReadAllMilestonesDto findAllOpenAndCloseMilestones(String issueStatus) {
        List<Milestone> findMilestones = milestoneRepository.findAll();
        List<Milestone> findIssueByMilestoneStatus = filterMilestoneStatus(findMilestones, issueStatus);

        return getResponseReadAllMilestoneDto(findIssueByMilestoneStatus, findMilestones);
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

    private List<Milestone> filterMilestoneStatus(List<Milestone> findMilestones, String milestoneStatus) {
        return findMilestones.stream()
            .filter(milestone -> milestone.getMilestoneStatus().toString().equals(milestoneStatus.toUpperCase()))
            .collect(Collectors.toList());
    }

    public ResponseReadAllMilestonesDto getResponseReadAllMilestoneDto(List<Milestone> findMilestones) {
        List<ResponseMilestoneDto> responseMilestoneDtos = responseMilestoneDtos(findMilestones);

        int allMilestoneCount = findMilestones.size();
        long openMilestoneCount = getOpenMilestonesCountByFindAll(findMilestones);
        long closeMilestoneCount = getCloseMilestonesCountByFindAll(findMilestones);

        return ResponseReadAllMilestonesDto.of(allMilestoneCount, openMilestoneCount, closeMilestoneCount, responseMilestoneDtos);
    }

    private ResponseReadAllMilestonesDto getResponseReadAllMilestoneDto(List<Milestone> findByMilestoneStatus, List<Milestone> findMilestones) {
        List<ResponseMilestoneDto> responseMilestoneDtos = responseMilestoneDtos(findByMilestoneStatus);

        int allMilestoneCount = findMilestones.size();
        long openMilestoneCount = getOpenMilestonesCountByFindAll(findMilestones);
        long closeMilestoneCount = getCloseMilestonesCountByFindAll(findMilestones);

        return ResponseReadAllMilestonesDto.of(allMilestoneCount, openMilestoneCount, closeMilestoneCount, responseMilestoneDtos);
    }

    private List<ResponseMilestoneDto> responseMilestoneDtos(List<Milestone> findMilestones) {
        return findMilestones.stream()
            .map(ResponseMilestoneDto::from)
            .collect(Collectors.toList());
    }

    private long getOpenMilestonesCountByFindAll(List<Milestone> findMilestones) {
        return findMilestones.stream().filter(milestone -> milestone.getMilestoneStatus().equals(MilestoneStatus.OPEN)).count();
    }

    private long getCloseMilestonesCountByFindAll(List<Milestone> findMilestones) {
        return findMilestones.stream().filter(milestone -> milestone.getMilestoneStatus().equals(MilestoneStatus.CLOSED)).count();
    }
}
