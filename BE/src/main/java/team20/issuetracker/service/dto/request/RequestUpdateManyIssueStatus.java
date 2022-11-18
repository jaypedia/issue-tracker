package team20.issuetracker.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.issue.IssueStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RequestUpdateManyIssueStatus {

    private List<Long> ids;

    @JsonProperty("action")
    private IssueStatus issueStatus;

    public static RequestUpdateManyIssueStatus of(List<Long> ids, IssueStatus status) {
        return new RequestUpdateManyIssueStatus(ids, status);
    }
}
