package team20.issuetracker.service;

import lombok.Getter;

public enum UpdateType {
    MILESTONE("milestone"),
    LABELS("labels"),
    ASSIGNEES("assignees");

    @Getter
    private final String type;

    UpdateType(String type) {
        this.type = type;
    }
}
