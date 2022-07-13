package team20.issuetracker.domain.assignee;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadAssigneeDto {

    private String image;
    private String title;

    public Assignee toEntity() {
        return Assignee.builder()
                .image(image)
                .title(title)
                .build();
    }
}
