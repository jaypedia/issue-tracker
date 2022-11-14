package team20.issuetracker.docs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.util.List;

import team20.issuetracker.controller.MilestoneController;
import team20.issuetracker.docs.config.RestDocsConfig;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneStatus;
import team20.issuetracker.service.MilestoneService;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseReadAllMilestonesDto;

@DisplayName("Spring Docs - 마일스톤")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(MilestoneController.class)
@Import(RestDocsConfig.class)
@ContextConfiguration(classes = {MilestoneController.class})
class MilestoneControllerDocTest {

    @MockBean
    private MilestoneService milestoneService;
    @Autowired
    private RestDocumentationResultHandler restDoc;

    private ObjectMapper mapper;
    private MockMvc mockMvc;

    public MilestoneControllerDocTest() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .uris()
                        .withScheme("https")
                        .withHost("api.rarus-be.com")
                        .withPort(443))
                .alwaysDo(print())
                .alwaysDo(restDoc)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("[Controller][POST] 마일스톤 저장 - 정상 호출")
    @Test
    void 마일스톤_저장() throws Exception {
        // given
        RequestSaveMilestoneDto dto = createMilestoneDto();
        given(milestoneService.save(any(RequestSaveMilestoneDto.class))).willReturn(1L);

        // when & then
        ResultActions result = mockMvc.perform(post("/api/milestones")
                .content(mapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        restDoc.document(
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("마일스톤의 제목"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("마일스톤 설명"),
                                        fieldWithPath("dueDate").type(JsonFieldType.STRING).description("마일스톤의 만료일")
                                )
                        )
                );
        then(milestoneService).should().save(any(RequestSaveMilestoneDto.class));
    }

    @DisplayName("[Controller][DELETE] 마일스톤 삭제 - 정상 호출")
    @Test
    void 마일스톤_삭제() throws Exception {
        // given
        Long milestoneId = 1L;
        willDoNothing().given(milestoneService).delete(milestoneId);

        // when & then
        mockMvc.perform(delete("/api/milestones/{milestoneId}", milestoneId))
                .andExpect(status().isOk())
                .andDo(
                        restDoc.document(
                                pathParameters(
                                        parameterWithName("milestoneId").description("삭제할 마일스톤 아이디")
                                )
                        )
                );

    }

    @DisplayName("[Controller][POST] 마일스톤 수정 - 정상 호출")
    @Test
    void 마일스톤_수정() throws Exception {
        // given
        Long milestoneId = 1L;
        RequestUpdateMilestoneDto requestUpdateDto = createUpdateMilestoneDto();
        given(milestoneService.update(eq(milestoneId), any(RequestUpdateMilestoneDto.class))).willReturn(milestoneId);

        // when & then
        mockMvc.perform(post("/api/milestones/{milestoneId}", milestoneId)
                        .content(mapper.writeValueAsString(requestUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDoc.document(
                                pathParameters(
                                        parameterWithName("milestoneId").description("수정할 마일스톤 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("업데이트 마일스톤 제목"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("업데이트 마일스톤 내용"),
                                        fieldWithPath("dueDate").type(JsonFieldType.STRING).description("업데이트 마일스톤 만료일"),
                                        fieldWithPath("milestoneStatus").type(JsonFieldType.STRING).description("업데이트 마일스톤 상태")
                                )
                        )
                );

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
        mockMvc.perform(get("/api/milestones"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDoc.document(
                                responseFields(
                                        fieldWithPath("allMilestoneCount").type(JsonFieldType.NUMBER).description("모든 마일스톤의 개수"),
                                        fieldWithPath("openMilestoneCount").type(JsonFieldType.NUMBER).description("오폰 상태의 마일스톤 개수"),
                                        fieldWithPath("closedMilestoneCount").type(JsonFieldType.NUMBER).description("닫힌 상태의 마일스톤 개수"),
                                        fieldWithPath("milestones").type(JsonFieldType.ARRAY).description("모든 마일스톤"),
                                        fieldWithPath("milestones.[].id").type(JsonFieldType.NUMBER).description("마일스톤의 아이디"),
                                        fieldWithPath("milestones.[].title").type(JsonFieldType.STRING).description("마일스톤의 제목"),
                                        fieldWithPath("milestones.[].dueDate").type(JsonFieldType.STRING).description("마일스톤의 만료일"),
                                        fieldWithPath("milestones.[].description").type(JsonFieldType.STRING).description("마일스톤의 설명"),
                                        fieldWithPath("milestones.[].milestoneStatus").type(JsonFieldType.STRING).description("마일스톤의 상태"),
                                        fieldWithPath("milestones.[].openIssueCount").type(JsonFieldType.NUMBER).description("마일스톤에 할당 되어 있는 오픈 상태의 이슈 개수"),
                                        fieldWithPath("milestones.[].closeIssueCount").type(JsonFieldType.NUMBER).description("마일스톤에 할당 되어 있는 닫힌 상태의 이슈 개수")
                                )
                        )
                );

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
        mockMvc.perform(get("/api/milestones/{milestoneId}", milestoneId))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDoc.document(
                                pathParameters(
                                        parameterWithName("milestoneId").description("조회할 마일스톤 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("마일스톤의 아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("마일스톤의 제목"),
                                        fieldWithPath("dueDate").type(JsonFieldType.STRING).description("마일스톤의 만료일"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("마일스톤의 설명"),
                                        fieldWithPath("milestoneStatus").type(JsonFieldType.STRING).description("마일스톤의 상태"),
                                        fieldWithPath("openIssueCount").type(JsonFieldType.NUMBER).description("마일스톤에 할당 되어 있는 오픈 상태의 이슈 개수"),
                                        fieldWithPath("closeIssueCount").type(JsonFieldType.NUMBER).description("마일스톤에 할당 되어 있는 닫힌 상태의 이슈 개수")
                                )
                        )
                );

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
        mockMvc.perform(get("/api/milestones")
                        .queryParam("is", milestoneStatus))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDoc.document(
                                requestParameters(
                                        parameterWithName("is").description("조회할 마일스톤의 상태")
                                ),
                                responseFields(
                                        fieldWithPath("allMilestoneCount").type(JsonFieldType.NUMBER).description("모든 마일스톤의 개수"),
                                        fieldWithPath("openMilestoneCount").type(JsonFieldType.NUMBER).description("오폰 상태의 마일스톤 개수"),
                                        fieldWithPath("closedMilestoneCount").type(JsonFieldType.NUMBER).description("닫힌 상태의 마일스톤 개수"),
                                        fieldWithPath("milestones").type(JsonFieldType.ARRAY).description("모든 마일스톤"),
                                        fieldWithPath("milestones.[].id").type(JsonFieldType.NUMBER).description("마일스톤의 아이디"),
                                        fieldWithPath("milestones.[].title").type(JsonFieldType.STRING).description("마일스톤의 제목"),
                                        fieldWithPath("milestones.[].dueDate").type(JsonFieldType.STRING).description("마일스톤의 만료일"),
                                        fieldWithPath("milestones.[].description").type(JsonFieldType.STRING).description("마일스톤의 설명"),
                                        fieldWithPath("milestones.[].milestoneStatus").type(JsonFieldType.STRING).description("마일스톤의 상태"),
                                        fieldWithPath("milestones.[].openIssueCount").type(JsonFieldType.NUMBER).description("마일스톤에 할당 되어 있는 오픈 상태의 이슈 개수"),
                                        fieldWithPath("milestones.[].closeIssueCount").type(JsonFieldType.NUMBER).description("마일스톤에 할당 되어 있는 닫힌 상태의 이슈 개수")
                                )
                        )
                );

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
        mockMvc.perform(get("/api/milestones").queryParam("is", milestoneStatus))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDoc.document(
                                requestParameters(
                                        parameterWithName("is").description("조회할 마일스톤의 상태")
                                ),
                                responseFields(
                                        fieldWithPath("allMilestoneCount").type(JsonFieldType.NUMBER).description("모든 마일스톤의 개수"),
                                        fieldWithPath("openMilestoneCount").type(JsonFieldType.NUMBER).description("오폰 상태의 마일스톤 개수"),
                                        fieldWithPath("closedMilestoneCount").type(JsonFieldType.NUMBER).description("닫힌 상태의 마일스톤 개수"),
                                        fieldWithPath("milestones").type(JsonFieldType.ARRAY).description("모든 마일스톤"),
                                        fieldWithPath("milestones.[].id").type(JsonFieldType.NUMBER).description("마일스톤의 아이디"),
                                        fieldWithPath("milestones.[].title").type(JsonFieldType.STRING).description("마일스톤의 제목"),
                                        fieldWithPath("milestones.[].dueDate").type(JsonFieldType.STRING).description("마일스톤의 만료일"),
                                        fieldWithPath("milestones.[].description").type(JsonFieldType.STRING).description("마일스톤의 설명"),
                                        fieldWithPath("milestones.[].milestoneStatus").type(JsonFieldType.STRING).description("마일스톤의 상태"),
                                        fieldWithPath("milestones.[].openIssueCount").type(JsonFieldType.NUMBER).description("마일스톤에 할당 되어 있는 오픈 상태의 이슈 개수"),
                                        fieldWithPath("milestones.[].closeIssueCount").type(JsonFieldType.NUMBER).description("마일스톤에 할당 되어 있는 닫힌 상태의 이슈 개수")
                                )
                        )
                );

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

    private RequestUpdateMilestoneDto createUpdateMilestoneDto() {
        String title = "title";
        String description = "description";
        LocalDate dueDate = LocalDate.now();
        MilestoneStatus milestoneStatus = MilestoneStatus.OPEN;

        return RequestUpdateMilestoneDto.of(title, description, dueDate, milestoneStatus);
    }
}
