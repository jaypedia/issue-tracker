package team20.issuetracker.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.issue.IssueStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUpdateManyIssueStatus {

    private List<Long> ids;

    @JsonProperty("action")
    private IssueStatus issueStatus;
}
