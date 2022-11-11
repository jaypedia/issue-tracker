package team20.issuetracker.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import team20.issuetracker.domain.milestone.MilestoneStatus;
import team20.issuetracker.login.jwt.JwtTokenProvider;
import team20.issuetracker.service.MilestoneService;
import team20.issuetracker.service.OauthService;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("컨트롤러 - 마일스톤 통합 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class MilestoneControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MilestoneService milestoneService;

    @Autowired
    OauthService oauthService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public MilestoneControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }

    @DisplayName("모든 마일스톤 API 호출 시 올바르지 않은 Access Token 을 받는다면 Status 로 302 Redirect 응답이 발생한다.")
    @Test
    void 리다이렉트_302_응답_발생() throws Exception {
        // when & then
        mvc.perform(get("/api/milestones")
            .header("Authorization", "None Access Token"))
            .andDo(print())
            .andExpect(status().is(302));
    }

    @DisplayName("[Controller][POST] 마일스톤 저장 - 정상 호출")
    @Test
    void 마일스톤_저장() throws Exception {
        // given
        mapper.registerModule(new JavaTimeModule());
        String title = "Milestone Title";
        String description = "Milestone Description";
        LocalDate dueDate = LocalDate.now();
        RequestSaveMilestoneDto saveDto = RequestSaveMilestoneDto.of(title, description, dueDate);

        // when & then
        mvc.perform(post("/api/milestones")
                .header("Authorization", "Test Access Token")
                .content(mapper.writeValueAsString(saveDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][GET] 모든 마일스톤 조회 - 정상 호출")
    @Test
    void 마일스톤_목록_조회() throws Exception {
        // given
        RequestSaveMilestoneDto saveMilestoneDto = saveOpenMilestoneDto();
        milestoneService.save(saveMilestoneDto);

        // when & then
        mvc.perform(get("/api/milestones")
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][GET] 열린 마일스톤 조회 - 정상 호출")
    @Test
    void 열린_마일스톤_목록_조회() throws Exception {
        // given
        RequestSaveMilestoneDto saveMilestoneDto = saveOpenMilestoneDto();
        milestoneService.save(saveMilestoneDto);
        milestoneService.save(saveMilestoneDto);

        // when & then
        mvc.perform(get("/api/milestones").queryParam("is", "open")
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][GET] 닫힌 마일스톤 조회 - 정상 호출")
    @Test
    void 닫힌_마일스톤_목록_조회() throws Exception {
        // given
        RequestSaveMilestoneDto saveMilestoneDto = saveOpenMilestoneDto();
        milestoneService.save(saveMilestoneDto);

        RequestUpdateMilestoneDto updateMilestoneDto = updateMilestoneDto();
        milestoneService.update(1L, updateMilestoneDto);

        // when & then
        mvc.perform(get("/api/milestones").queryParam("is", "closed")
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][GET] 마일스톤 단일 조회 - 정상 호출")
    @Test
    void 마일스톤_단일_조회() throws Exception {
        // given
        RequestSaveMilestoneDto saveMilestoneDto = saveOpenMilestoneDto();
        Long saveMilestoneId = milestoneService.save(saveMilestoneDto);

        // when & then
        mvc.perform(get("/api/milestones/" + saveMilestoneId)
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][DELETE] 마일스톤 삭제 - 정상 호출")
    @Test
    void 마일스톤_삭제() throws Exception {
        // given
        RequestSaveMilestoneDto saveMilestoneDto = saveOpenMilestoneDto();
        milestoneService.save(saveMilestoneDto);

        // when & then
        mvc.perform(delete("/api/milestones/" + 1L)
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][POST] 마일스톤 수정 - 정상 호출")
    @Test
    void 마일스톤_수정() throws Exception {
        // given
        mapper.registerModule(new JavaTimeModule());
        RequestSaveMilestoneDto saveMilestoneDto = saveOpenMilestoneDto();
        milestoneService.save(saveMilestoneDto);

        RequestUpdateMilestoneDto updateMilestoneDto = updateMilestoneDto();
        // when & then
        mvc.perform(post("/api/milestones/" + 1L)
                .header("Authorization", "Test Access Token")
                .content(mapper.writeValueAsString(updateMilestoneDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    RequestSaveMilestoneDto saveOpenMilestoneDto() {
        String title = "Milestone Title";
        String description = "Milestone Description";
        LocalDate dueDate = LocalDate.now();

        return RequestSaveMilestoneDto.of(title, description, dueDate);
    }

    RequestUpdateMilestoneDto updateMilestoneDto() {
        String updateTitle = "Update Milestone Title";
        String updateDescription = "Update Milestone Description";
        LocalDate updateDueDate = LocalDate.now().plusDays(1);
        MilestoneStatus updateStatus = MilestoneStatus.CLOSED;

        return RequestUpdateMilestoneDto.of(updateTitle, updateDescription, updateDueDate, updateStatus);
    }
}
