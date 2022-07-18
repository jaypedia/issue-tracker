package team20.issuetracker.domain.label;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseLabelsDto {

    private List<ResponseLabelDto> labels;

    public static ResponseLabelsDto from(List<ResponseLabelDto> labels) {
        return new ResponseLabelsDto(labels);
    }
}
