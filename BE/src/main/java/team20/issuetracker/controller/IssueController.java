package team20.issuetracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
import team20.issuetracker.service.IssueService;
import team20.issuetracker.service.dto.response.ResponseIssueDto;
import team20.issuetracker.service.dto.response.ResponseReadAllIssueDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/issues")
@RestController
public class IssueController {

    private final IssueService issueService;

    @PostMapping("/write")
    public ResponseEntity<Long> save(@RequestBody RequestSaveIssueDto requestSaveIssueDto) {
        Long issueId = issueService.save(requestSaveIssueDto);

        return ResponseEntity.ok(issueId);
    }

    // TODO - Issue 전체 조회
    @GetMapping
    public ResponseEntity<ResponseReadAllIssueDto> read(HttpServletRequest request) {
        List<ResponseIssueDto> responseIssueDtos = issueService.findAll();
        ResponseReadAllIssueDto responseReadAllIssuesDto = issueService.getAllIssueData(responseIssueDtos);

        return ResponseEntity.ok(responseReadAllIssuesDto);
    }

    // TODO - Issue 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseIssueDto> detail(@PathVariable Long id, HttpServletRequest request) {
        String oauthId = request.getAttribute("oauthId").toString();
        ResponseIssueDto responseIssueDto = issueService.detail(oauthId, id);

        return ResponseEntity.ok(responseIssueDto);
    }

}