package team20.issuetracker.domain.label;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLabelDto {

    private String title;
    private String description;
    private String backgroundColor;
    private String textColor;
}
