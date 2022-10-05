package team20.issuetracker.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team20.issuetracker.domain.assginee.Assignee;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseAssigneeDto {

    private Long id;
    private String userId;
    private String image;

    public static ResponseAssigneeDto from(Assignee assignee) {
        return new ResponseAssigneeDto(assignee.getId(), assignee.getUserId(), assignee.getImage());
    }
}
