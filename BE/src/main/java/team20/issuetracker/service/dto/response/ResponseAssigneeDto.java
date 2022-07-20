package team20.issuetracker.service.dto.response;

import lombok.Getter;
import team20.issuetracker.domain.assginee.Assignee;

@Getter
public class ResponseAssigneeDto {

    private Long id;
    private String title;
    private String image;

    private ResponseAssigneeDto(Long id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public static ResponseAssigneeDto from(Assignee assignee) {
        return new ResponseAssigneeDto(assignee.getId(), assignee.getTitle(), assignee.getImage());
    }
}
