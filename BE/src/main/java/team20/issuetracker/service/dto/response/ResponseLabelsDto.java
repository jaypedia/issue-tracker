package team20.issuetracker.service.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseLabelsDto {

    private int labelCount;
    private List<ResponseLabelDto> labels;

    public static ResponseLabelsDto from(List<ResponseLabelDto> labels) {
        return new ResponseLabelsDto(labels.size(), labels);
    }
}
