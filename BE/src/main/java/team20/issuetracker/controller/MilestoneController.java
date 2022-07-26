package team20.issuetracker.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseReadAllMilestonesDto;
import team20.issuetracker.service.MilestoneService;
import team20.issuetracker.util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/milestones")
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody @Valid RequestSaveMilestoneDto requestSaveMilestoneDto, HttpServletRequest request) {
        Long milestoneId = milestoneService.save(requestSaveMilestoneDto);

        return ResponseEntity.ok(milestoneId);
    }

    @GetMapping
    public ResponseEntity<ResponseReadAllMilestonesDto> read(HttpServletRequest request) {
        String oauthId = request.getAttribute("oauthId").toString();
        List<ResponseMilestoneDto> responseMilestoneDtos = milestoneService.findAll(oauthId);
        ResponseReadAllMilestonesDto responseReadAllMilestonesDto = milestoneService.getAllMilestoneData(responseMilestoneDtos);

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
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody @Valid RequestUpdateMilestoneDto requestUpdateMilestoneDto) {
        Long milestoneId = milestoneService.update(id, requestUpdateMilestoneDto);

        return ResponseEntity.ok(milestoneId);
    }
}
