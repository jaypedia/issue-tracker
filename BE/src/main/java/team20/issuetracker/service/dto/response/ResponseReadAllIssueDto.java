package team20.issuetracker.service.dto.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team20.issuetracker.controller.page.CustomPage;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseReadAllIssueDto {

    private long openIssueCount;
    private long closedIssueCount;
    private List<ResponseIssueDto> issues;
    private CustomPage pageable;

    public static ResponseReadAllIssueDto of(long openIssueCount, long closedIssueCount, Page<ResponseIssueDto> issues) {
        return new ResponseReadAllIssueDto(openIssueCount, closedIssueCount, issues.getContent(), CustomPage.of(new PageImpl<>(issues.getContent(), issues.getPageable(), issues.getTotalElements())));
    }
}
