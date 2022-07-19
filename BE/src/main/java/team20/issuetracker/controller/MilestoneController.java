package team20.issuetracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team20.issuetracker.domain.milestone.requestDto.SaveMilestoneDto;
import team20.issuetracker.service.MilestoneService;

@RequiredArgsConstructor
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping("/milestones")
    public ResponseEntity<Long> save(@RequestBody SaveMilestoneDto saveMilestoneDto) {
        Long mileStoneId = milestoneService.save(saveMilestoneDto);

        return ResponseEntity.ok(mileStoneId);
    }
}