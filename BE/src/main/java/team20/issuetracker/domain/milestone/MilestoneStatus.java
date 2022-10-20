package team20.issuetracker.domain.milestone;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum MilestoneStatus {
    OPEN, CLOSED;

    @JsonCreator
    public static MilestoneStatus from(String status) {
        return MilestoneStatus.valueOf(status.toUpperCase());
    }
}
