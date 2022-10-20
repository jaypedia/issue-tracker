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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.controller.page.CustomPageable;
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

    @GetMapping(params = "q")
    public ResponseEntity<ResponseReadAllIssueDto> readIssuesByCondition(
        @RequestParam(value = "page", required = false, defaultValue = "1") String page,
        @RequestParam("q") String condition){

        PageRequest pageRequest = CustomPageable.defaultPage(page);
        String decode = URLDecoder.decode(condition, StandardCharsets.UTF_8);
        ResponseReadAllIssueDto responseIssueDto = issueService.findAllIssuesByCondition(decode, pageRequest);

        return ResponseEntity.ok(responseIssueDto);
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
}
