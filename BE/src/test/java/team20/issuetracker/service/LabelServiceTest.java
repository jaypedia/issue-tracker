package team20.issuetracker.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.service.dto.request.RequestLabelDto;
import team20.issuetracker.service.dto.response.ResponseLabelDto;

@DisplayName("비즈니스 로직 - 레이블")
@ExtendWith(MockitoExtension.class)
class LabelServiceTest {

    @InjectMocks
    private LabelService sut;

    @Mock
    private LabelRepository labelRepository;

    @DisplayName("레이블을 조회하면, 모든 레이블 리스트를 반환한다.")
    @Test
    void 레이블_조회() throws Exception {
        // given
        Label expected = createLabel(1L, "labelTitle1");
        given(labelRepository.findAll()).willReturn(List.of(expected));

        // when
        List<ResponseLabelDto> actual = sut.findAll().getLabels();

        // then
        assertThat(actual)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("title", expected.getTitle());

        then(labelRepository).should().findAll();
    }

    @DisplayName("수정할 레이블을 찾고 레이블 정보를 입력하면, 입력한 정보대로 해당 레이블을 수정한다.")
    @Test
    void 레이블_수정() throws Exception {
        // given
        Long labelId= 1L;
        String oldTitle = "Old Title";
        String updateTitle = "Update Title";
        Label label = createLabel(labelId, oldTitle);
        RequestLabelDto updateData = createRequestLabelDto(updateTitle);
        given(labelRepository.findById(label.getId())).willReturn(Optional.of(label));

        // when
        sut.update(label.getId(), updateData);

        // then
        assertThat(label.getTitle())
            .isNotEqualTo(oldTitle)
            .isEqualTo(updateTitle);

        then(labelRepository).should().findById(label.getId());
    }

    @DisplayName("수정할 레이블을 찾을 수 없다면 예외를 발생시킨다.")
    @Test
    void 레이블_수정_실패() throws Exception {
        // given
        Long wrongLabelId = 2L;
        String title = "Label Title";
        RequestLabelDto dto = createRequestLabelDto(title);
        given(labelRepository.findById(wrongLabelId)).willThrow(new IllegalArgumentException("존재하지 않는 Label 입니다."));

        // when
        assertThatThrownBy(() -> sut.update(wrongLabelId, dto)).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("존재하지 않는 Label 입니다.");

        // then
        then(labelRepository).should().findById(wrongLabelId);
    }

    @DisplayName("RequestLabelDto 필드를 토대로 레이블 Entity 를 만들고, 레이블을 저장한다.")
    @Test
    void 레이블_저장() throws Exception {
        // given
        Label label = createLabel(1L, "1번 레이블");
        RequestLabelDto dto = createRequestLabelDto("1번 레이블");
        given(labelRepository.save(any(Label.class))).willReturn(label);

        // when
        sut.save(dto);

        // then
        then(labelRepository).should().save(any(Label.class));
    }

    @DisplayName("레이블의 Id를 입력하면, 해당 레이블은 삭제된다.")
    @Test
    void 레이블_삭제() throws Exception {
        // given
        Long labelId = 1L;
        willDoNothing().given(labelRepository).deleteById(labelId);

        // when
        labelRepository.deleteById(labelId);

        // then
        then(labelRepository).should().deleteById(labelId);
    }

    @DisplayName("삭제할 레이블을 찾을 수 없다면 예외를 발생시킨다.")
    @Test
    void 레이블_삭제_실패() throws Exception {
        // given
        Long wrongLabelId = 2L;
        given(labelRepository.findById(wrongLabelId)).willThrow(new IllegalArgumentException("존재하지 않는 Label 입니다."));

        // when
        assertThatThrownBy(() -> sut.delete(wrongLabelId)).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("존재하지 않는 Label 입니다.");

        // then
        then(labelRepository).should().findById(wrongLabelId);
    }

    private RequestLabelDto createRequestLabelDto(String title) {
        return RequestLabelDto.of(
                title,
                "#123456",
                "#123456",
                "레이블이다.");
    }

    private Label createLabel(Long id, String title) {
        return Label.of(
                id,
                title,
                "#123456",
                "#123456",
                "레이블 1입니다."
        );
    }
}
