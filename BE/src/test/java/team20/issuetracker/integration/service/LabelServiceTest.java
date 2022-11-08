package team20.issuetracker.integration.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.service.dto.request.RequestLabelDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[통합 테스트] 마일스톤 테스트")
@Transactional
@SpringBootTest
public class LabelServiceTest {

    @Autowired
    private LabelRepository labelRepository;

    @DisplayName("레이블 정보를 입력하면, 레이블을 저장한다.")
    @Test
    void 레이블_저장() {
        // given
        Label label = createLabel();

        // when
        Label saveLabel = labelRepository.save(label);

        // then
        assertThat(saveLabel.getId()).isEqualTo(label.getId());
    }

    @DisplayName("레이블 ID 를 찾아 해당 레이블을 삭제한다.")
    @Test
    void 레이블_삭제() {
        // given
        Label label = createLabel();
        Label saveLabel = labelRepository.save(label);
        Label findLabel = labelRepository.findById(saveLabel.getId())
            .orElseThrow(() -> new CheckEntityException("존재하지 않는 Label 입니다.", HttpStatus.BAD_REQUEST));

        // when
        labelRepository.deleteById(findLabel.getId());
    }

    @DisplayName("레이블 ID 를 찾아 해당 레이블을 수정한다.")
    @Test
    void 레이블_수정() {
        // given
        Label label = createLabel();
        Label saveLabel = labelRepository.save(label);
        RequestLabelDto updateDto = createLabelDto();

        Label findLabel = labelRepository.findById(saveLabel.getId())
            .orElseThrow(() -> new CheckEntityException("존재하지 않는 Label 입니다.", HttpStatus.BAD_REQUEST));

        // when
        saveLabel.update(updateDto);

        // then
        assertThat(findLabel.getTitle()).isEqualTo("Update Label Title");
        assertThat(findLabel.getDescription()).isEqualTo("Update Label Description");
        assertThat(findLabel.getTextColor()).isEqualTo("#222222");
        assertThat(findLabel.getBackgroundColor()).isEqualTo("#333333");
    }

    @Test
    void 레이블_목록_조회() {
        // given
        Label label = createLabel();
        Label saveLabel = labelRepository.save(label);

        // when
        List<Label> labels = labelRepository.findAll();

        // then
        assertThat(labels).hasSize(1);
        assertThat(labels.get(0)).isEqualTo(saveLabel);
    }

    private Label createLabel() {
        String title = "Label Title";
        String textColor = "#111111";
        String backgroundColor = "#222222";
        String description = "Label Description";
        RequestLabelDto dto = RequestLabelDto.of(title, backgroundColor, textColor, description);

        return Label.of(dto.getTitle(), dto.getTextColor(), dto.getBackgroundColor(), dto.getDescription());
    }

    private RequestLabelDto createLabelDto() {
        String updateTitle = "Update Label Title";
        String updateTextColor = "#222222";
        String updateBackgroundColor = "#333333";
        String updateDescription = "Update Label Description";

        return RequestLabelDto.of(updateTitle, updateBackgroundColor, updateTextColor, updateDescription);
    }
}
