package team20.issuetracker.service.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseAssigneesDto {

    private List<ResponseAssigneeDto> assignees;

    public static ResponseAssigneesDto from(List<ResponseAssigneeDto> assignees) {
        return new ResponseAssigneesDto(assignees);
    }
}
