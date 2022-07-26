package team20.issuetracker.service.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseReadAllIssueDto {

    private long openIssueCount;
    private long closedIssueCount;
    private long labelCount;
    private List<ResponseIssueDto> issues;

    private ResponseReadAllIssueDto(long openIssueCount, long closedIssueCount, long labelCount, List<ResponseIssueDto> issues) {
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
        this.labelCount = labelCount;
        this.issues = issues;
    }

    public static ResponseReadAllIssueDto of(long openIssueCount, long closedIssueCount, long labelCount, List<ResponseIssueDto> issues) {
        return new ResponseReadAllIssueDto(openIssueCount, closedIssueCount, labelCount, issues);
    }
}
