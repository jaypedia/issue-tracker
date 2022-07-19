package team20.issuetracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team20.issuetracker.domain.milestone.request.SaveMilestoneDto;
import team20.issuetracker.domain.milestone.response.MilestoneDto;
import team20.issuetracker.domain.milestone.response.ResponseMilestone;
import team20.issuetracker.service.MilestoneService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/milestones")
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody SaveMilestoneDto saveMilestoneDto) {
        Long mileStoneId = milestoneService.save(saveMilestoneDto);

        return ResponseEntity.ok(mileStoneId);
    }

    @GetMapping
    public ResponseEntity<ResponseMilestone> read() {
        List<MilestoneDto> milestoneDto = milestoneService.findAll();
        ResponseMilestone responseMilestone = milestoneService.getAllMilestoneData(milestoneDto);

        return ResponseEntity.ok(responseMilestone);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        milestoneService.delete(id);
    }
}