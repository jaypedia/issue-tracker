package team20.issuetracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.request.SaveMilestoneDto;
import team20.issuetracker.domain.milestone.request.UpdateMilestoneDto;
import team20.issuetracker.domain.milestone.response.MilestoneDto;
import team20.issuetracker.domain.milestone.response.ReadAllMilestones;
import team20.issuetracker.service.MilestoneService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/milestones")
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody SaveMilestoneDto saveMilestoneDto) {
        Long milestoneId = milestoneService.save(saveMilestoneDto);

        return ResponseEntity.ok(milestoneId);
    }

    @GetMapping
    public ResponseEntity<ReadAllMilestones> read() {
        List<MilestoneDto> milestoneDto = milestoneService.findAll();
        ReadAllMilestones readAllMilestones = milestoneService.getAllMilestoneData(milestoneDto);

        return ResponseEntity.ok(readAllMilestones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MilestoneDto> detail(@PathVariable Long id) {
        MilestoneDto findMilestone = milestoneService.detail(id);

        return ResponseEntity.ok(findMilestone);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        milestoneService.delete(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody UpdateMilestoneDto updateMilestoneDto) {
        Long milestoneId = milestoneService.update(id, updateMilestoneDto);

        return ResponseEntity.ok(milestoneId);
    }
}