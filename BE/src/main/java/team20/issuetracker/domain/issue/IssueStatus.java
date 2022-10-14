package team20.issuetracker.domain.issue;

import lombok.Getter;

public enum IssueStatus {
    OPEN("open"),
    CLOSED("closed");

    @Getter
    private final String status;

    IssueStatus(String status) {
        this.status = status;
    }
}
