package team20.issuetracker.domain.label;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseLabelDto {

    private Long id;
    private String title;
    private String description;
    private String backgroundColor;
    private String textColor;

    private ResponseLabelDto(Label label) {
        this.id = label.getId();
        this.title = label.getTitle();
        this.description = label.getDescription();
        this.backgroundColor = label.getBackgroundColor();
        this.textColor = label.getTextColor();
    }

    public static ResponseLabelDto form(Label label) {
        return new ResponseLabelDto(label.getId(),
                label.getTitle(),
                label.getDescription(),
                label.getBackgroundColor(),
                label.getTextColor());
    }
}
