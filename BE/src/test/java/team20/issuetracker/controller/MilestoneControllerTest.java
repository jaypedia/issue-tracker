package team20.issuetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneStatus;
import team20.issuetracker.service.MilestoneService;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseReadAllMilestonesDto;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("컨트롤러 - 마일스톤")
@WebMvcTest(MilestoneController.class)
@ContextConfiguration(classes = MilestoneController.class)
public class MilestoneControllerTest {

    private final MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private MilestoneService milestoneService;

    public MilestoneControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[Controller][POST] 마일스톤 저장 - 정상 호출")
    @Test
    void 마일스톤_저장() throws Exception {
        // given
        RequestSaveMilestoneDto dto = createMilestoneDto();
        given(milestoneService.save(any(RequestSaveMilestoneDto.class))).willReturn(1L);

        // when & then
        mapper.registerModule(new JavaTimeModule());
        ResultActions result = mvc.perform(post("/api/milestones")
            .content(mapper.writeValueAsString(dto))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(1L)));

        then(milestoneService).should().save(any(RequestSaveMilestoneDto.class));
    }

    @DisplayName("[Controller][POST] title 없는 마일스톤 저장 - 실패")
    @Test
    void 마일스톤_제목_NotEmpty_실패() throws Exception {
        // given
        RequestSaveMilestoneDto dto = createEmptyTitleMilestoneDto();

        // when & then
        mapper.registerModule(new JavaTimeModule());
        ResultActions result = mvc.perform(post("/api/milestones")
            .content(mapper.writeValueAsString(dto))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @DisplayName("[Controller][POST] title size 초과 마일스톤 저장 - 실패")
    @Test
    void 마일스톤_제목_Size_초과_실패() throws Exception {
        // given
        RequestSaveMilestoneDto dto = createOverSizeTitleMilestoneDto();

        // when & then
        mapper.registerModule(new JavaTimeModule());
        ResultActions result = mvc.perform(post("/api/milestones")
            .content(mapper.writeValueAsString(dto))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @DisplayName("[Controller][POST] content size 초과 마일스톤 저장 - 실패")
    @Test
    void 마일스톤_본문_Size_초과_실패() throws Exception {
        // given
        RequestSaveMilestoneDto dto = createOverSizeContentMilestoneDto();

        // when & then
        mapper.registerModule(new JavaTimeModule());
        ResultActions result = mvc.perform(post("/api/milestones")
            .content(mapper.writeValueAsString(dto))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @DisplayName("[Controller][DELETE] 마일스톤 삭제 - 정상 호출")
    @Test
    void 마일스톤_삭제() throws Exception {
        // given
        Long milestoneId = 1L;
        willDoNothing().given(milestoneService).delete(milestoneId);

        // when & then
        mapper.registerModule(new JavaTimeModule());
        mvc.perform(delete("/api/milestones/" + milestoneId))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][POST] 마일스톤 수정 - 정상 호출")
    @Test
    void 마일스톤_수정() throws Exception {
        // given
        Long milestoneId = 1L;
        RequestUpdateMilestoneDto requestUpdateDto = createUpdateMilestoneDto();
        given(milestoneService.update(eq(milestoneId), any(RequestUpdateMilestoneDto.class))).willReturn(milestoneId);

        // when & then
        mapper.registerModule(new JavaTimeModule());
        ResultActions result = mvc.perform(post("/api/milestones/" + milestoneId)
            .content(mapper.writeValueAsString(requestUpdateDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        result.andDo(print())
            .andExpect(status().isOk());

        then(milestoneService).should().update(eq(milestoneId), any(RequestUpdateMilestoneDto.class));
    }

    @DisplayName("[Controller][GET] 마일스톤 전체 조회 - 정상 호출")
    @Test
    void 마일스톤_전체조회() throws Exception {
        // given
        Milestone milestone = createOpenMilestone();
        ResponseMilestoneDto responseMilestoneDto = ResponseMilestoneDto.from(milestone);
        ResponseReadAllMilestonesDto responseReadAllMilestonesDto =
            ResponseReadAllMilestonesDto.of(1, 1, 0, List.of(responseMilestoneDto));

        given(milestoneService.findAll()).willReturn(responseReadAllMilestonesDto);

        // when & then
        mvc.perform(get("/api/milestones"))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.milestones[0].id", is(responseMilestoneDto.getId().intValue())))
            .andExpect(status().isOk());

        then(milestoneService).should().findAll();
    }

    @DisplayName("[Controller][GET] 마일스톤 단일 조회 - 정상 호출")
    @Test
    void 마일스톤_단일조회() throws Exception {
        // given
        Long milestoneId = 1L;
        Milestone milestone = createOpenMilestone();
        ResponseMilestoneDto responseMilestoneDto = ResponseMilestoneDto.from(milestone);
        given(milestoneService.detail(milestoneId)).willReturn(responseMilestoneDto);

        // when & then
        mvc.perform(get("/api/milestones/" + milestoneId))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(responseMilestoneDto.getId().intValue())))
            .andExpect(status().isOk());

        then(milestoneService).should().detail(milestoneId);
    }

    @DisplayName("[Controller][GET] 열려있는 마일스톤 조회 - 정상 호출")
    @Test
    void 열려있는_마일스톤_조회() throws Exception {
        // given
        String milestoneStatus = "open";
        Milestone milestone = createOpenMilestone();
        ResponseMilestoneDto responseMilestoneDto = ResponseMilestoneDto.from(milestone);
        ResponseReadAllMilestonesDto responseReadAllMilestonesDto =
            ResponseReadAllMilestonesDto.of(1, 1, 0, List.of(responseMilestoneDto));

        given(milestoneService.findAllOpenAndCloseMilestones(milestoneStatus)).willReturn(responseReadAllMilestonesDto);

        // when & then
        mvc.perform(get("/api/milestones")
                .queryParam("is", milestoneStatus))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.milestones[0].milestoneStatus", is(responseMilestoneDto.getMilestoneStatus())))
            .andExpect(status().isOk());

        then(milestoneService).should().findAllOpenAndCloseMilestones(milestoneStatus);
    }

    @DisplayName("[Controller][GET] 닫혀있는 마일스톤 조회 - 정상 호출")
    @Test
    void 닫혀있는_마일스톤_조회() throws Exception {
        // given
        String milestoneStatus = "closed";
        Milestone milestone = createCloseMilestone();
        ResponseMilestoneDto responseMilestoneDto = ResponseMilestoneDto.from(milestone);
        ResponseReadAllMilestonesDto responseReadAllMilestonesDto =
            ResponseReadAllMilestonesDto.of(1, 0, 1, List.of(responseMilestoneDto));

        given(milestoneService.findAllOpenAndCloseMilestones(milestoneStatus)).willReturn(responseReadAllMilestonesDto);

        // when & then
        mvc.perform(get("/api/milestones").queryParam("is", milestoneStatus))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.milestones[0].milestoneStatus", is(responseMilestoneDto.getMilestoneStatus())))
            .andExpect(status().isOk());

        then(milestoneService).should().findAllOpenAndCloseMilestones(milestoneStatus);
    }

    private Milestone createOpenMilestone() {
        Long milestoneId = 1L;
        String milestoneTitle = "Milestone Title";
        String milestoneDescription = "Milestone Description";
        LocalDate dueDate = LocalDate.now();

        return Milestone.of(milestoneId, milestoneTitle, dueDate, milestoneDescription);
    }

    private Milestone createCloseMilestone() {
        Long milestoneId = 1L;
        String milestoneTitle = "Milestone Title";
        String milestoneDescription = "Milestone Description";
        LocalDate dueDate = LocalDate.now();
        MilestoneStatus milestoneStatus = MilestoneStatus.CLOSED;

        return Milestone.of(milestoneId, milestoneTitle, dueDate, milestoneDescription, milestoneStatus);
    }

    private RequestSaveMilestoneDto createMilestoneDto() {
        String title = "Milestone Title";
        String description = "Milestone Description";
        LocalDate dueDate = LocalDate.now();

        return RequestSaveMilestoneDto.of(title, description, dueDate);
    }

    private RequestSaveMilestoneDto createEmptyTitleMilestoneDto() {
        String title = "";
        String description = "Milestone Description";
        LocalDate dueDate = LocalDate.now();

        return RequestSaveMilestoneDto.of(title, description, dueDate);
    }

    private RequestSaveMilestoneDto createOverSizeTitleMilestoneDto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 51; i++) {
            sb.append(i);
        }

        String title = sb.toString();
        String description = "Milestone Description";
        LocalDate dueDate = LocalDate.now();

        return RequestSaveMilestoneDto.of(title, description, dueDate);
    }

    private RequestSaveMilestoneDto createOverSizeContentMilestoneDto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 801; i++) {
            sb.append(i);
        }

        String title = "title";
        String description = sb.toString();
        LocalDate dueDate = LocalDate.now();

        return RequestSaveMilestoneDto.of(title, description, dueDate);
    }

    private RequestUpdateMilestoneDto createUpdateMilestoneDto() {
        String title = "title";
        String description = "description";
        LocalDate dueDate = LocalDate.now();
        MilestoneStatus milestoneStatus = MilestoneStatus.OPEN;

        return RequestUpdateMilestoneDto.of(title, description, dueDate, milestoneStatus);
    }
}
