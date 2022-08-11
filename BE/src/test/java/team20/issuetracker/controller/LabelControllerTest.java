package team20.issuetracker.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import team20.issuetracker.domain.label.Label;
import team20.issuetracker.service.LabelService;
import team20.issuetracker.service.dto.request.RequestLabelDto;
import team20.issuetracker.service.dto.response.ResponseLabelDto;
import team20.issuetracker.service.dto.response.ResponseLabelsDto;

@DisplayName("컨트롤러 - 레이블")
@WebMvcTest(LabelController.class)
@ContextConfiguration(classes = LabelController.class)
class LabelControllerTest {

    private final MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private LabelService labelService;

    public LabelControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[Controller][GET] 레이블 리스트 전체 조회 - 정상 호출")
    @Test
    void givenNothing_whenRequestingLabels_thenReturnLabels() throws Exception {
        //given
        Label label = createLabel();
        ResponseLabelDto responseLabelDto = createResponseLabelDto(label);
        ResponseLabelsDto responseLabelsDto = createResponseLabelsDto(responseLabelDto);
        given(labelService.findAll()).willReturn(responseLabelsDto);

        //when & then
        mvc.perform(get("/api/labels"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.labelCount", is(responseLabelsDto.getLabelCount())))
                .andExpect(jsonPath("$.labels").value(IsNull.notNullValue()));
        then(labelService).should().findAll();
    }

    @DisplayName("[Controller][POST] 새로운 레이블 작성 - 정상 작성")
    @Test
    void givenNothing_whenRequestingLabel_thenReturnLabelId() throws Exception {
        //given
        RequestLabelDto requestLabelDto = createRequestLabelDto();
        given(labelService.save(any(RequestLabelDto.class))).willReturn(1L);

        //when & then
        mvc.perform(post("/api/labels")
                        .content(mapper.writeValueAsString(requestLabelDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(String.valueOf(1L)));
        then(labelService).should().save(any(RequestLabelDto.class));
    }

    @DisplayName("[Controller][POST] 레이블 업데이트 - 정상 작동")
    @Test
    void givenLabelId_whenRequesting_thenReturnLabelId() throws Exception {
        //given
        Long labelId = 1L;
        RequestLabelDto requestLabelDto = createRequestLabelDto();
        given(labelService.update(eq(labelId), any(RequestLabelDto.class))).willReturn(labelId);

        //when & then
        mvc.perform(post("/api/labels/" + labelId)
                        .content(mapper.writeValueAsString(requestLabelDto))
                        .queryParam("id", String.valueOf(labelId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(String.valueOf(labelId)));
        then(labelService).should().update(eq(labelId), any(RequestLabelDto.class));
    }

    @DisplayName("[Controller][DELETE] 레이블 삭제 - 정상 작동")
    @Test
    void givenLabelId_whenRequesting_thenDeleteLabel() throws Exception {
        //given
        Long labelId = 1L;
        willDoNothing().given(labelService).delete(labelId);

        //when & then
        mvc.perform(delete("/api/labels/" + labelId)
                        .queryParam("id", String.valueOf(labelId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        then(labelService).should().delete(labelId);
    }

    private RequestLabelDto createRequestLabelDto() {
        return RequestLabelDto.of(
                "새로운 레이블 제목",
                "#121212",
                "#121212",
                "새로운 레이블 작성 테스트를 위한 레이블"
        );
    }

    private ResponseLabelsDto createResponseLabelsDto(ResponseLabelDto responseLabelDto) {
        return ResponseLabelsDto.from(List.of(responseLabelDto));
    }

    private ResponseLabelDto createResponseLabelDto(Label label) {
        return ResponseLabelDto.from(label);
    }

    private Label createLabel() {
        return Label.of(
                1L,
                "레이블 제목",
                "#123456",
                "#123456",
                "레이블 설명"
        );
    }
}
