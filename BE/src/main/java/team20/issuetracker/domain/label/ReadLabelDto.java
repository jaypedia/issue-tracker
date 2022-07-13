package team20.issuetracker.domain.label;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadLabelDto {

    private String title;
    private String textColor;
    private String backgroundColor;
    private String description;

    public Label toEntity() {
        return Label.builder()
            .textColor(textColor)
            .title(title)
            .description(description)
            .backgroundColor(backgroundColor)
            .build();
    }
}
