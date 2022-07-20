package team20.issuetracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseReadAllMilestonesDto;
import team20.issuetracker.service.MilestoneService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/milestones")
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody RequestSaveMilestoneDto requestSaveMilestoneDto) {
        Long milestoneId = milestoneService.save(requestSaveMilestoneDto);

        return ResponseEntity.ok(milestoneId);
    }

    @GetMapping
    public ResponseEntity<ResponseReadAllMilestonesDto> read() {
        List<ResponseMilestoneDto> responseMilestoneDto = milestoneService.findAll();
        ResponseReadAllMilestonesDto responseReadAllMilestonesDto = milestoneService.getAllMilestoneData(responseMilestoneDto);

        return ResponseEntity.ok(responseReadAllMilestonesDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMilestoneDto> detail(@PathVariable Long id) {
        ResponseMilestoneDto findMilestone = milestoneService.detail(id);

        return ResponseEntity.ok(findMilestone);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        milestoneService.delete(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody RequestUpdateMilestoneDto requestUpdateMilestoneDto) {
        Long milestoneId = milestoneService.update(id, requestUpdateMilestoneDto);

        return ResponseEntity.ok(milestoneId);
    }
}
