package team20.issuetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.service.MilestoneService;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseReadAllMilestonesDto;

@RequiredArgsConstructor
@RequestMapping("/api/milestones")
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody @Valid RequestSaveMilestoneDto requestSaveMilestoneDto) {
        Long milestoneId = milestoneService.save(requestSaveMilestoneDto);

        return ResponseEntity.ok(milestoneId);
    }

    @GetMapping
    public ResponseEntity<ResponseReadAllMilestonesDto> read() {
        ResponseReadAllMilestonesDto findAllMilestones = milestoneService.findAll();

        return ResponseEntity.ok(findAllMilestones);
    }

    @GetMapping(params = "is")
    public ResponseEntity<ResponseReadAllMilestonesDto> readOpenAndClosedMilestones(@RequestParam("is") String status) {
        ResponseReadAllMilestonesDto findOpenAndClosedMilestones= milestoneService.findAllOpenAndCloseMilestones(status);

        return ResponseEntity.ok(findOpenAndClosedMilestones);
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
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody @Valid RequestUpdateMilestoneDto requestUpdateMilestoneDto) {
        Long milestoneId = milestoneService.update(id, requestUpdateMilestoneDto);

        return ResponseEntity.ok(milestoneId);
    }
}
