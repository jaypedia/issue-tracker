package team20.issuetracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team20.issuetracker.service.IssueService;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleDto;
import team20.issuetracker.service.dto.response.ResponseIssueDto;
import team20.issuetracker.service.dto.response.ResponseReadAllIssueDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public ResponseEntity<ResponseReadAllIssueDto> read() {
        List<ResponseIssueDto> responseIssueDtos = issueService.findAll();
        ResponseReadAllIssueDto responseReadAllIssuesDto = issueService.getAllIssueData(responseIssueDtos);

        return ResponseEntity.ok(responseReadAllIssuesDto);
    }

    // TODO - Open Issue 전체 조회
    @GetMapping("/open")
    public ResponseEntity<ResponseReadAllIssueDto> readOpenIssue() {
        List<ResponseIssueDto> findAllIssues = issueService.findAll();
        List<ResponseIssueDto> findAllOpenIssues = issueService.findAllOpenIssue();
        ResponseReadAllIssueDto responseReadAllOpenIssueDto = issueService.getAllIOpenIssueData(findAllOpenIssues, findAllIssues);

        return ResponseEntity.ok(responseReadAllOpenIssueDto);
    }

    // TODO - Close Issue 전체 조회
    @GetMapping("/close")
    public ResponseEntity<ResponseReadAllIssueDto> readCloseIssue() {
        List<ResponseIssueDto> findAllIssues = issueService.findAll();
        List<ResponseIssueDto> findAllCloseIssues = issueService.findAllCloseIssue();
        ResponseReadAllIssueDto responseReadAllCloseIssueDto = issueService.getAllICloseIssueData(findAllCloseIssues, findAllIssues);

        return ResponseEntity.ok(responseReadAllCloseIssueDto);
    }

    // TODO - Issue 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseIssueDto> detail(@PathVariable Long id) {
        ResponseIssueDto responseIssueDto = issueService.detail(id);

        return ResponseEntity.ok(responseIssueDto);
    }

    // TODO - 특정 Issue 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        issueService.delete(id);
    }

    // TODO - 특정 Issue Title 변경
    //  RequestUpdateIssueTitleDto @Valid 추가해서 Validation 필요
    @PostMapping("/{id}")
    public ResponseEntity<Long> updateTitle(@PathVariable Long id,
                                            @Valid @RequestBody RequestUpdateIssueTitleDto requestUpdateIssueTitleDto) {
        Long updateIssueId = issueService.updateTitle(id, requestUpdateIssueTitleDto);

        return ResponseEntity.ok(updateIssueId);
    }
}