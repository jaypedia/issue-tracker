package team20.issuetracker.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.controller.page.CustomPageable;
import team20.issuetracker.domain.issue.IssueStatus;
import team20.issuetracker.service.IssueService;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleWithContentDto;
import team20.issuetracker.service.dto.request.RequestUpdateManyIssueStatus;
import team20.issuetracker.service.dto.response.ResponseIssueDto;
import team20.issuetracker.service.dto.response.ResponseReadAllIssueDto;

@RequiredArgsConstructor
@RequestMapping("/api/issues")
@RestController
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<Long> save(@Valid @RequestBody RequestSaveIssueDto requestSaveIssueDto) {
        Long issueId = issueService.save(requestSaveIssueDto);

        return ResponseEntity.ok(issueId);
    }

    @GetMapping(params = "is")
    public ResponseEntity<ResponseReadAllIssueDto> readOpenAndClosedIssues(@RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam("is") IssueStatus status) {
        PageRequest pageRequest = CustomPageable.defaultPage(page);
        ResponseReadAllIssueDto findOpenAndCloseIssues = issueService.findAllOpenAndCloseIssues(pageRequest, status);
        return ResponseEntity.ok(findOpenAndCloseIssues);
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
    public ResponseEntity<Long> updateTitleWithContent(@PathVariable Long id,
                                                       @Valid @RequestBody RequestUpdateIssueTitleWithContentDto requestUpdateIssueTitleWithContentDto) {
        Long updateIssueId = issueService.updateTitleWithContent(id, requestUpdateIssueTitleWithContentDto);

        return ResponseEntity.ok(updateIssueId);
    }

    @PostMapping("/{id}/{updateType}")
    public ResponseEntity<Long> updateIssueRelated(@PathVariable Long id, @PathVariable String updateType,
                                                   @Valid @RequestBody RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto) {
        Long updateIssueId = issueService.updateIssueRelated(id, updateType, requestUpdateIssueRelatedDto);

        return ResponseEntity.ok(updateIssueId);
    }

    @PostMapping("/action")
    public void updateManyIssueStatus(@RequestBody RequestUpdateManyIssueStatus requestUpdateManyIssueStatus) {
        issueService.updateManyIssueStatus(requestUpdateManyIssueStatus);
    }

//    @GetMapping(params = "title")
//    public ResponseEntity<ResponseReadAllIssueDto> searchIssuesByTitle(@RequestParam String title) {
//        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.findAllSearchIssues(title);
//
//        return ResponseEntity.ok(responseReadAllIssueDto);
//    }

//    @GetMapping(params = {"title", "status"})
//    public ResponseEntity<ResponseReadAllIssueDto> searchStatusIssuesByTitle(
//            @RequestParam String title,
//            @RequestParam String status) {
//        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.findAllSearchStatusIssues(title, status);
//
//        return ResponseEntity.ok(responseReadAllIssueDto);
//    }

//    @GetMapping("/commented_by/me")
//    public ResponseEntity<ResponseReadAllIssueDto> filterCommentByIssues(HttpServletRequest request) {
//        String oauthId = request.getAttribute("oauthId").toString();
//        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.filterCommentByMeIssue(oauthId);
//
//        return ResponseEntity.ok(responseReadAllIssueDto);
//    }

//    @GetMapping(value = "/commented_by/me", params = "status")
//    public ResponseEntity<ResponseReadAllIssueDto> filterCommentByStatusIssues(
//            HttpServletRequest request,
//            @RequestParam String status) {
//        String oauthId = request.getAttribute("oauthId").toString();
//        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.filterCommentByMeStatusIssue(oauthId, status);
//
//        return ResponseEntity.ok(responseReadAllIssueDto);
//    }

//    @GetMapping("/created_by/me")
//    public ResponseEntity<ResponseReadAllIssueDto> filterMyAllIssues(HttpServletRequest request) {
//        String oauthId = request.getAttribute("oauthId").toString();
//        ResponseReadAllIssueDto responseIssueDtos = issueService.findAllMyIssues(oauthId);
//
//        return ResponseEntity.ok(responseIssueDtos);
//    }

//    @GetMapping(value = "/created_by/me", params = "status")
//    public ResponseEntity<ResponseReadAllIssueDto> filterMyAllStatusIssues(
//            HttpServletRequest request,
//            @RequestParam String status) {
//
//        String oauthId = request.getAttribute("oauthId").toString();
//
//        ResponseReadAllIssueDto responseIssueDtos = issueService.findAllMyStatusIssues(oauthId, status);
//
//        return ResponseEntity.ok(responseIssueDtos);
//    }

//    @GetMapping("assignee_by/me")
//    public ResponseEntity<ResponseReadAllIssueDto> filterAssigneeByMeIssues(
//            HttpServletRequest request) {
//        String oauthId = request.getAttribute("oauthId").toString();
//        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.findAssigneeByMeIssues(oauthId);
//
//        return ResponseEntity.ok(responseReadAllIssueDto);
//    }

//    @GetMapping(value = "assignee_by/me", params = "status")
//    public ResponseEntity<ResponseReadAllIssueDto> filterAssigneeByMeStatusIssue(
//            HttpServletRequest request,
//            @RequestParam String status) {
//
//        String oauthId = request.getAttribute("oauthId").toString();
//        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.findAssigneeByMeStatusIssues(oauthId, status);
//
//        return ResponseEntity.ok(responseReadAllIssueDto);
//    }
}
