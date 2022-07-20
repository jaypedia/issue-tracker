package team20.issuetracker.service.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ResponseAssigneesDto {

    private List<ResponseAssigneeDto> assignees;

    private ResponseAssigneesDto(List<ResponseAssigneeDto> assignees) {
        this.assignees = assignees;
    }

    public static ResponseAssigneesDto from(List<ResponseAssigneeDto> assignees) {
        return new ResponseAssigneesDto(assignees);
    }
}
