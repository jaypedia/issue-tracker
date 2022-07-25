package team20.issuetracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import com.mysql.cj.util.TestUtils;

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
    void givenFindAll_whenSearchingAllLabel_thenReturnsLabel() throws Exception {
        //given
        Label expected = createLabel(1L, "labelTitle1");
        given(labelRepository.findAll()).willReturn(List.of(expected));

        //when
        List<ResponseLabelDto> actual = sut.findAll().getLabels();

        //then
        assertThat(actual)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("title", expected.getTitle());
        then(labelRepository).should().findAll();
    }

    @DisplayName("레이블 정보를 입력하면, 해당 레이블을 수정한다.")
    @Test
    void givenLabelInfo_whenUpdatingLabel_thenUpdatedLabel() throws Exception {
        //given
        Long labelId = 1L;
        String oldTitle = "labelTitle1";
        String updateTitle = "labelTitle2";
        Label label = createLabel(labelId, oldTitle);
        RequestLabelDto dto = createRequestLabelDto(updateTitle);
        given(labelRepository.findById(labelId)).willReturn(Optional.of(label));

        //when
        labelRepository.findById(labelId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 Label 입니다.");
        }).update(dto);

        //then
        assertThat(label.getTitle())
                .isNotEqualTo(oldTitle)
                .isEqualTo(updateTitle);

        then(labelRepository).should().findById(labelId);
    }

    @DisplayName("레이블 정보를 입력하면, 레이블을 저장한다.")
    @Test
    void givenLabelInfo_whenSavingLabel_thenSavesLabel() throws Exception {
        //given
        Label tmpLabel = createLabel(1L, "1번 레이블");
        RequestLabelDto requestLabelDto = createRequestLabelDto("1번 레이블");
        given(labelRepository.save(any(Label.class))).willReturn(tmpLabel);

        //when
        sut.save(requestLabelDto);

        //then
        then(labelRepository).should().save(any(Label.class));
    }

    @DisplayName("레이블의 Id를 입력하면, 해당 레이블은 삭제된다.")
    @Test
    void givenLabelId_whenDeletingLabel_thenDeletesLabel() throws Exception {
        //given
        Long labelId = 1L;
        willDoNothing().given(labelRepository).deleteById(labelId);

        //when
        labelRepository.deleteById(labelId);

        //then
        then(labelRepository).should().deleteById(labelId);
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
