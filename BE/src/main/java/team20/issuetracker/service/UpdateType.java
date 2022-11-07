package team20.issuetracker.service;

import lombok.Getter;

import org.springframework.http.HttpStatus;

import team20.issuetracker.exception.CheckUpdateTypeException;

public enum UpdateType {
    MILESTONE("milestone"),
    LABELS("labels"),
    ASSIGNEES("assignees");

    @Getter
    private final String type;

    UpdateType(String type) {
        this.type = type;
    }

    public static UpdateType checkUpdateType(String updateType) {
        UpdateType[] values = UpdateType.values();
        for (UpdateType value : values) {
            if (value.getType().equals(updateType.toUpperCase())) {
                return value;
            }
        }

        throw new CheckUpdateTypeException("해당 UpdateType 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
