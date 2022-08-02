package team20.issuetracker.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.service.dto.request.RequestLabelDto;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseAssigneeDto {

    private Long id;
    private String title;
    private String image;

    public static ResponseAssigneeDto from(Assignee assignee) {
        return new ResponseAssigneeDto(assignee.getId(), assignee.getTitle(), assignee.getImage());
    }
}
