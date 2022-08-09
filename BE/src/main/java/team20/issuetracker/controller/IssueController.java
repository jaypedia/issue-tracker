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
import team20.issuetracker.service.IssueService;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleDto;
import team20.issuetracker.service.dto.response.ResponseIssueDto;
import team20.issuetracker.service.dto.response.ResponseReadAllIssueDto;

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

    @GetMapping
    public ResponseEntity<ResponseReadAllIssueDto> read() {
        ResponseReadAllIssueDto responseIssueDtos = issueService.findAll();

        return ResponseEntity.ok(responseIssueDtos);
    }

    @GetMapping("/open")
    public ResponseEntity<ResponseReadAllIssueDto> readOpenIssue() {
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.findAllOpenIssue();

        return ResponseEntity.ok(responseReadAllIssueDto);
    }

    @GetMapping("/close")
    public ResponseEntity<ResponseReadAllIssueDto> readCloseIssue() {
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.findAllCloseIssue();

        return ResponseEntity.ok(responseReadAllIssueDto);
    }

    @GetMapping(value = "/search", params = "title")
    public ResponseEntity<ResponseReadAllIssueDto> searchIssuesByTitle(@RequestParam String title) {
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.findAllSearchIssues(title);

        return ResponseEntity.ok(responseReadAllIssueDto);
    }

    @GetMapping(value = "/search", params = {"title", "issueStatus"})
    public ResponseEntity<ResponseReadAllIssueDto> searchStatusIssuesByTitle(
            @RequestParam String title,
            @RequestParam String issueStatus) {
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.findAllSearchStatusIssues(title, issueStatus);

        return ResponseEntity.ok(responseReadAllIssueDto);
    }

    @GetMapping("/filter/commentBy")
    public ResponseEntity<ResponseReadAllIssueDto> filterCommentByIssues(HttpServletRequest request) {
        String oauthId = request.getAttribute("oauthId").toString();
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.filterCommentByMeIssue(oauthId);

        return ResponseEntity.ok(responseReadAllIssueDto);
    }

    @GetMapping(value = "/filter/commentBy", params = "issueStatus")
    public ResponseEntity<ResponseReadAllIssueDto> filterCommentByStatusIssues(
            HttpServletRequest request,
            @RequestParam String issueStatus) {
        String oauthId = request.getAttribute("oauthId").toString();
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.filterCommentByMeStatusIssue(oauthId, issueStatus);

        return ResponseEntity.ok(responseReadAllIssueDto);
    }

    @GetMapping("/filter/createBy")
    public ResponseEntity<ResponseReadAllIssueDto> filterMyAllIssues(HttpServletRequest request) {
        String oauthId = request.getAttribute("oauthId").toString();
        ResponseReadAllIssueDto responseIssueDtos = issueService.findAllMyIssues(oauthId);

        return ResponseEntity.ok(responseIssueDtos);
    }

    @GetMapping(value = "/filter/createBy", params = "issueStatus")
    public ResponseEntity<ResponseReadAllIssueDto> filterMyAllStatusIssues(
            HttpServletRequest request,
            @RequestParam String issueStatus) {

        String oauthId = request.getAttribute("oauthId").toString();

        ResponseReadAllIssueDto responseIssueDtos = issueService.findAllMyStatusIssues(oauthId, issueStatus);

        return ResponseEntity.ok(responseIssueDtos);
    }

    @GetMapping("/filter/AssigneeBy")
    public ResponseEntity<ResponseReadAllIssueDto> filterAssigneeByMeIssues(
            HttpServletRequest request) {
        String oauthId = request.getAttribute("oauthId").toString();
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.findAssigneeByMeIssues(oauthId);

        return ResponseEntity.ok(responseReadAllIssueDto);
    }

    @GetMapping(value = "/filter/AssigneeBy", params = {"issueStatus"})
    public ResponseEntity<ResponseReadAllIssueDto> filterAssigneeByMeStatusIssue(
            HttpServletRequest request,
            @RequestParam String issueStatus) {

        String oauthId = request.getAttribute("oauthId").toString();
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.findAssigneeByMeStatusIssues(oauthId, issueStatus);

        return ResponseEntity.ok(responseReadAllIssueDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseIssueDto> detail(@PathVariable Long id) {
        ResponseIssueDto responseIssueDto = issueService.detail(id);

        return ResponseEntity.ok(responseIssueDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        issueService.delete(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Long> updateTitle(@PathVariable Long id,
                                            @Valid @RequestBody RequestUpdateIssueTitleDto requestUpdateIssueTitleDto) {
        Long updateIssueId = issueService.updateTitle(id, requestUpdateIssueTitleDto);

        return ResponseEntity.ok(updateIssueId);
    }
}
