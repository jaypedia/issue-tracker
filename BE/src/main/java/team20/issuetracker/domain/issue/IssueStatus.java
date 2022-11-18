package team20.issuetracker.domain.issue;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum IssueStatus {
    OPEN, CLOSED;

    @JsonCreator
    public static IssueStatus from(String status) {
        return IssueStatus.valueOf(status.toUpperCase());
    }
}
