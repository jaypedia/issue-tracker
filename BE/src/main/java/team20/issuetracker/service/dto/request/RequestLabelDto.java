package team20.issuetracker.service.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestLabelDto {

    @NotEmpty
    @Size(max = 20, message = "Label의 제목은 20글자를 넘을 수 없습니다.")
    private String title;
    @Pattern(regexp = "#[\\dA-Fa-f]{6}", message = "올바른 color 코드가 아닙니다.")
    private String backgroundColor;
    @Pattern(regexp = "#[\\dA-Fa-f]{6}", message = "올바른 color 코드가 아닙니다.")
    private String textColor;

    @Size(max = 100, message = "Label의 설명은 100글자를 넘을 수 없습니다.")
    private String description;

    private RequestLabelDto(String title, String backgroundColor, String textColor, String description) {
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.description = description;
    }

    public static RequestLabelDto of(String title, String backgroundColor, String textColor, String description) {
        return new RequestLabelDto(title, backgroundColor, textColor, description);
    }
}
