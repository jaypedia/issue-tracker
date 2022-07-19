package team20.issuetracker.domain.label;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveLabelDto {

    private String title;
    private String description;
    private String backgroundColor;
    private String textColor;
}
