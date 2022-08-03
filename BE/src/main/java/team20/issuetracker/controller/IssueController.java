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

    // TODO - 특정 Title 로 모든 이슈 검색
    //  title 에 대한 검증 필요
    @GetMapping(value = "/search", params = "title")
    public ResponseEntity<ResponseReadAllIssueDto> searchIssuesByTitle(@RequestParam String title) {
        List<ResponseIssueDto> findAllSearchIssues = issueService.findAllSearchIssues(title);
        ResponseReadAllIssueDto responseReadAllSearchIssueDto = issueService.getAllIssueData(findAllSearchIssues);

        return ResponseEntity.ok(responseReadAllSearchIssueDto);
    }

    // TODO - 특정 Title 에 해당하며 열려있거나 닫혀있는 이슈를 판단해 조회
    //  title, issueStatus 에 대한 검증 필요
    @GetMapping(value = "/search", params = {"title", "issueStatus"})
    public ResponseEntity<ResponseReadAllIssueDto> searchStatusIssuesByTitle(
            @RequestParam String title,
            @RequestParam String issueStatus) {

        List<ResponseIssueDto> findAllIssues = issueService.findAll();
        List<ResponseIssueDto> findAllSearchOpenIssues = issueService.findAllSearchStatusIssues(title, issueStatus);
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.getAllSearchStatusIssueData(findAllSearchOpenIssues, findAllIssues);

        return ResponseEntity.ok(responseReadAllIssueDto);
    }

    // TODO - 내가 작성한 모든 이슈 필터링 후 조회
    @GetMapping(value = "/filter/createBy")
    public ResponseEntity<ResponseReadAllIssueDto> filterMyAllIssues(HttpServletRequest request) {
        String oauthId = request.getAttribute("oauthId").toString();
        List<ResponseIssueDto> responseIssueDtos = issueService.findAllMyIssues(oauthId);
        ResponseReadAllIssueDto responseReadAllIssuesDto = issueService.getAllIssueData(responseIssueDtos);

        return ResponseEntity.ok(responseReadAllIssuesDto);
    }

    // TODO - 내가 작성한 모든 열린 또는 닫힌 이슈 필터링 후 조회
    //  issueStatus 검증 필요
    @GetMapping(value = "/filter/createBy", params = "issueStatus")
    public ResponseEntity<ResponseReadAllIssueDto> filterMyAllStatusIssues(
            HttpServletRequest request,
            @RequestParam String issueStatus) {

        String oauthId = request.getAttribute("oauthId").toString();
        List<ResponseIssueDto> findAllIssues = issueService.findAll();
        List<ResponseIssueDto> responseIssueDtos = issueService.findAllMyStatusIssues(oauthId, issueStatus);
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.getStatusFilterAllIssueData(responseIssueDtos, findAllIssues);

        return ResponseEntity.ok(responseReadAllIssueDto);
    }

    // TODO - 내가 할당된 모든 이슈 조회
    @GetMapping(value = "/filter/AssigneeBy")
    public ResponseEntity<ResponseReadAllIssueDto> filterAssigneeByMeIssues(
            HttpServletRequest request) {

        String oauthId = request.getAttribute("oauthId").toString();
        List<ResponseIssueDto> responseIssueDtos = issueService.findAssigneeByMeIssues(oauthId);
        ResponseReadAllIssueDto responseReadAllIssueDto = issueService.getAllIssueData(responseIssueDtos);

        return ResponseEntity.ok(responseReadAllIssueDto);
    }


    // TODO - Issue 상세 조회
    //  id 에 대한 검증 필요
    @GetMapping("/{id}")
    public ResponseEntity<ResponseIssueDto> detail(@PathVariable Long id) {
        ResponseIssueDto responseIssueDto = issueService.detail(id);

        return ResponseEntity.ok(responseIssueDto);
    }

    // TODO - 특정 Issue 삭제
    //  id 에 대한 검증 필요
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        issueService.delete(id);
    }

    // TODO - 특정 Issue Title 변경
    //  id 에 대한 검증 필요
    @PostMapping("/{id}")
    public ResponseEntity<Long> updateTitle(@PathVariable Long id,
                                            @Valid @RequestBody RequestUpdateIssueTitleDto requestUpdateIssueTitleDto) {
        Long updateIssueId = issueService.updateTitle(id, requestUpdateIssueTitleDto);

        return ResponseEntity.ok(updateIssueId);
    }
}