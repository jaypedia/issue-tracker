package team20.issuetracker.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.config.DatabaseCleanup;
import team20.issuetracker.service.LabelService;
import team20.issuetracker.service.dto.request.RequestLabelDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("컨트롤러 - 레이블 통합 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class LabelControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public LabelControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private LabelService labelService;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }

    @DisplayName("모든 레이블 API 호출 시 올바르지 않은 Access Token 을 받는다면 Status 로 302 Redirect 응답이 발생한다.")
    @Test
    void 리다이렉트_302_응답_발생() throws Exception {
        // when & then
        mvc.perform(get("/api/milestones")
                .header("Authorization", "None Access Token"))
            .andDo(print())
            .andExpect(status().is(302));
    }

    @DisplayName("[Controller][GET] 레이블 리스트 전체 조회 - 정상 호출")
    @Test
    void 모든_레이블_조회() throws Exception {
        // given
        RequestLabelDto saveDto = saveLabelDto();
        labelService.save(saveDto);

        // when & then
        mvc.perform(get("/api/labels")
                .header("Authorization", "Test Access Token")
            .content(mapper.writeValueAsString(saveDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][POST] 새로운 레이블 작성 - 정상 작성")
    @Test
    void 레이블_저장() throws Exception {
        // given
        RequestLabelDto saveDto = saveLabelDto();

        // when & then
        mvc.perform(post("/api/labels")
                .header("Authorization", "Test Access Token")
            .content(mapper.writeValueAsString(saveDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][POST] 레이블 업데이트 - 정상 작동")
    @Test
    void 레이블_수정() throws Exception {
        // given
        RequestLabelDto labelDto = saveLabelDto();
        Long saveLabelId = labelService.save(labelDto);

        RequestLabelDto updateDto
            = RequestLabelDto.of("Update Label Title", "#333333", "#444444", "Update Label Description");

        // when & then
        mvc.perform(post("/api/labels/" + saveLabelId)
                .header("Authorization", "Test Access Token")
            .content(mapper.writeValueAsString(updateDto))
            .contentType(MediaType. APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][DELETE] 레이블 삭제 - 정상 작동")
    @Test
    void 레이블_삭제() throws Exception {
        // given
        RequestLabelDto saveDto = saveLabelDto();
        Long saveLabelId = labelService.save(saveDto);

        // when & then
        mvc.perform(delete("/api/labels/" + saveLabelId)
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());

    }

    private RequestLabelDto saveLabelDto() {
        String title = "Label Title";
        String backgroundColor = "#111111";
        String textColor = "#222222";
        String description = "Label Description";

        return RequestLabelDto.of(title, backgroundColor, textColor, description);
    }
}