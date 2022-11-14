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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import team20.issuetracker.controller.IssueController;
import team20.issuetracker.controller.page.CustomPageable;
import team20.issuetracker.docs.config.RestDocsConfig;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueStatus;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.login.oauth.Role;
import team20.issuetracker.service.IssueService;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleWithContentDto;
import team20.issuetracker.service.dto.request.RequestUpdateManyIssueStatus;
import team20.issuetracker.service.dto.response.ResponseIssueDto;
import team20.issuetracker.service.dto.response.ResponseReadAllIssueDto;

@DisplayName("Spring Docs - 이슈")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(IssueController.class)
@Import(RestDocsConfig.class)
@ContextConfiguration(classes = {IssueController.class})
class IssueControllerDocTest {

    @Autowired
    private RestDocumentationResultHandler restDocs;
    @MockBean
    private IssueService issueService;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    public IssueControllerDocTest() {
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
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("[Controller][POST] 새로운 이슈 작성 - 정상 호출")
    @Test
    void 이슈_작성() throws Exception {
        // given
        Long issueId = 1L;
        RequestSaveIssueDto requestSaveIssueDto = createRequestSaveIssueDto();
        given(issueService.save(any(RequestSaveIssueDto.class))).willReturn(issueId);

        // when & then
        mockMvc.perform(post("/api/issues")
                        .content(mapper.writeValueAsString(requestSaveIssueDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(issueId)))
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("이슈 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("이슈 내용"),
                                        fieldWithPath("assigneeIds").type(JsonFieldType.ARRAY).description("이슈 담당자 아이디"),
                                        fieldWithPath("labelIds").type(JsonFieldType.ARRAY).description("레이블 아이디"),
                                        fieldWithPath("milestoneIds").type(JsonFieldType.ARRAY).description("마일스톤 아이디")
                                )
                        )
                );

        then(issueService).should().save(any(RequestSaveIssueDto.class));
    }

    @DisplayName("[Controller][DELETE] 이슈 삭제 - 정상 호출")
    @Test
    void 이슈_삭제() throws Exception {
        // given
        Long issueId = 1L;
        willDoNothing().given(issueService).delete(issueId);

        // when & then
        mockMvc.perform(delete("/api/issues/{issueId}", issueId))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("issueId").description("삭제할 이슈의 아이디")
                                )
                        )
                );

        then(issueService).should().delete(issueId);
    }

    @DisplayName("[Controller][POST] 이슈 제목 및 내용 수정 - 정상 호출")
    @Test
    void 이슈_제목_내용_수정_성공() throws Exception {
        // given
        Long issueId = 1L;
        RequestUpdateIssueTitleWithContentDto requestUpdateIssueTitleWithContentDto = createRequestUpdateIssueTitleWithContentDto();
        given(issueService.updateTitleWithContent(eq(issueId), any(RequestUpdateIssueTitleWithContentDto.class))).willReturn(1L);

        // when & then
        mockMvc.perform(post("/api/issues/{issueId}", issueId)
                        .content(mapper.writeValueAsString(requestUpdateIssueTitleWithContentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("issueId").description("수정할 이슈의 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("업데이트 이슈 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("업데이트 이슈 내용")
                                )
                        )
                );

        then(issueService).should().updateTitleWithContent(eq(issueId), any(RequestUpdateIssueTitleWithContentDto.class));
    }

    @DisplayName("[Controller][POST] 이슈 내부의 연관된 마일스톤 수정 - 정상 호출")
    @Test
    void 이슈내부의_연관된_마일스톤_수정() throws Exception {
        // given
        Long issueId = 1L;
        String updateType = "milestone";
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto = createRequestUpdateIssueRelatedDto();

        given(issueService.updateIssueRelated(eq(issueId), eq(updateType), any(RequestUpdateIssueRelatedDto.class))).willReturn(issueId);

        // when & then
        mockMvc.perform(post("/api/issues/{issueId}/{updateType}", issueId, updateType)
                        .content(mapper.writeValueAsString(requestUpdateIssueRelatedDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("issueId").description("수정할 이슈의 아이디"),
                                        parameterWithName("updateType").description("수정할 이슈의 요소 타입")),
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("업데이트 마일스톤 아이디")
                                )
                        )
                );

        then(issueService).should().updateIssueRelated(eq(issueId), eq(updateType), any(RequestUpdateIssueRelatedDto.class));
    }

    @DisplayName("[Controller][POST] 이슈 내부의 연관된 레이블 수정 - 정상 호출")
    @Test
    void 이슈내부의_연관된_레이블_수정() throws Exception {
        // given
        Long issueId = 1L;
        String updateType = "labels";
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto = createRequestUpdateIssueRelatedDto();

        given(issueService.updateIssueRelated(eq(issueId), eq(updateType), any(RequestUpdateIssueRelatedDto.class))).willReturn(issueId);

        // when & then
        mockMvc.perform(post("/api/issues/{issueId}/{updateType}", issueId, updateType)
                        .content(mapper.writeValueAsString(requestUpdateIssueRelatedDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("issueId").description("수정할 이슈의 아이디"),
                                        parameterWithName("updateType").description("수정할 이슈의 요소 타입")),
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("업데이트 레이블 아이디")
                                )
                        )
                );

        then(issueService).should().updateIssueRelated(eq(issueId), eq(updateType), any(RequestUpdateIssueRelatedDto.class));
    }

    @DisplayName("[Controller][POST] 이슈 내부의 연관된 담당자 수정 - 정상 호출")
    @Test
    void 이슈내부의_연관된_담당자_수정() throws Exception {
        // given
        Long issueId = 1L;
        String updateType = "assignees";
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto = createRequestUpdateIssueRelatedDto();

        given(issueService.updateIssueRelated(eq(issueId), eq(updateType), any(RequestUpdateIssueRelatedDto.class))).willReturn(issueId);

        // when & then
        mockMvc.perform(post("/api/issues/{issueId}/{updateType}", issueId, updateType)
                        .content(mapper.writeValueAsString(requestUpdateIssueRelatedDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("issueId").description("수정할 이슈의 아이디"),
                                        parameterWithName("updateType").description("수정할 이슈의 요소 타입")),
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("업데이트 담당자 아이디"))
                        )
                );

        then(issueService).should().updateIssueRelated(eq(issueId), eq(updateType), any(RequestUpdateIssueRelatedDto.class));
    }

    @DisplayName("[Controller][POST] 단일 또는 다수의 이슈 수정 - 정상 호출")
    @Test
    void 이슈_단일_또는_다수_수정() throws Exception {
        // given
        RequestUpdateManyIssueStatus requestUpdateManyIssueStatusDto = createRequestUpdateManyIssueStatusDto();
        willDoNothing().given(issueService).updateManyIssueStatus(any(RequestUpdateManyIssueStatus.class));

        // when & then
        mockMvc.perform(post("/api/issues/action")
                        .content(mapper.writeValueAsString(requestUpdateManyIssueStatusDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("ids").type(JsonFieldType.ARRAY).description("수정할 이슈의 아이디 리스트"),
                                        fieldWithPath("action").type(JsonFieldType.STRING).description("업데이트 이슈 상태"))
                        )
                );

        then(issueService).should().updateManyIssueStatus(any(RequestUpdateManyIssueStatus.class));
    }

    @DisplayName("[Controller][GET] 이슈 상세조회 - 정상 호출")
    @Test
    void 이슈_상세조회() throws Exception {
        // given
        Long issueId = 1L;
        RequestSaveIssueDto newRequestDto = createRequestSaveIssueDto();
        Milestone newMilestone = getMilestone();
        Member newMember = getMember();
        List<Assignee> assignees = getAssignees();
        List<Label> labels = getLabels();

        Issue newIssue = Issue.of(issueId, newRequestDto.getTitle(), newRequestDto.getContent(), newMilestone);
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        newIssue.addMember(newMember);
        newMilestone.updateIssue(newIssue);
        ResponseIssueDto responseIssueDto = ResponseIssueDto.of(newIssue);

        given(issueService.detail(issueId)).willReturn(responseIssueDto);

        // when & then
        mockMvc.perform(get("/api/issues/{issueId}", issueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("issueId").description("상세 조회할 이슈 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("조회된 이슈의 아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("조회된 이슈의 제목"),
                                        fieldWithPath("author").type(JsonFieldType.STRING).description("조회된 이슈의 작성자 이름"),
                                        fieldWithPath("image").type(JsonFieldType.STRING).description("조회된 이슈의 작성자 프로필 URL"),
                                        fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("조회된 이슈에 작성된 댓글 개수"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("조회된 이슈의 내용"),
                                        fieldWithPath("createdAt").type(JsonFieldType.NULL).description("조회된 이슈의 작성일"),
                                        fieldWithPath("issueStatus").type(JsonFieldType.STRING).description("조회된 이슈의 상태"),
                                        fieldWithPath("milestones").type(JsonFieldType.ARRAY).description("이슈에 등록된 마일스톤"),
                                        fieldWithPath("milestones.[].id").type(JsonFieldType.NUMBER).description("해당 마일스톤 아이디"),
                                        fieldWithPath("milestones.[].title").type(JsonFieldType.STRING).description("해당 마일스톤 제목"),
                                        fieldWithPath("milestones.[].dueDate").type(JsonFieldType.STRING).description("해당 마일스톤 만료일"),
                                        fieldWithPath("milestones.[].milestoneStatus").type(JsonFieldType.STRING).description("해당 마일스톤 상태"),
                                        fieldWithPath("milestones.[].description").type(JsonFieldType.STRING).description("해당 마일스톤의 설명"),
                                        fieldWithPath("milestones.[].openIssueCount").type(JsonFieldType.NUMBER).description("해당 마일스톤에 등록되어 있는 OPEN 상태의 이슈 개수"),
                                        fieldWithPath("milestones.[].closeIssueCount").type(JsonFieldType.NUMBER).description("해당 마일스톤에 등록되어 있는 CLOSE 상태의 이슈 개수"),
                                        fieldWithPath("comments").type(JsonFieldType.ARRAY).description("이슈에 등록된 댓글"),
                                        fieldWithPath("labels").type(JsonFieldType.ARRAY).description("이슈에 등록된 레이블"),
                                        fieldWithPath("labels.[].id").type(JsonFieldType.NUMBER).description("해당 레이블의 아이디"),
                                        fieldWithPath("labels.[].title").type(JsonFieldType.STRING).description("해당 레이블의 제목"),
                                        fieldWithPath("labels.[].description").type(JsonFieldType.STRING).description("해당 레이블의 설명"),
                                        fieldWithPath("labels.[].backgroundColor").type(JsonFieldType.STRING).description("해당 레이블의 배경색"),
                                        fieldWithPath("labels.[].textColor").type(JsonFieldType.STRING).description("해당 레이블의 색"),
                                        fieldWithPath("assignees").type(JsonFieldType.ARRAY).description("이슈에 등록된 담당자"),
                                        fieldWithPath("assignees.[].id").type(JsonFieldType.NUMBER).description("해당 담당자의 아이디"),
                                        fieldWithPath("assignees.[].userId").type(JsonFieldType.STRING).description("해당 담당자의 닉네임"),
                                        fieldWithPath("assignees.[].image").type(JsonFieldType.STRING).description("해당 담당자의 프로필 URL")
                                )
                        )
                );

        then(issueService).should().detail(issueId);
    }

    @DisplayName("[Controller][GET] 이슈 필터조회 - 정상 호출")
    @Test
    void 이슈_필터_조회() throws Exception {
        // give
        Long issueId = 1L;
        RequestSaveIssueDto newRequestDto = createRequestSaveIssueDto();
        Milestone newMilestone = getMilestone();
        Member newMember = getMember();
        List<Assignee> assignees = getAssignees();
        List<Label> labels = getLabels();

        Issue newIssue = Issue.of(issueId, newRequestDto.getTitle(), newRequestDto.getContent(), newMilestone);
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        newIssue.addMember(newMember);
        newMilestone.updateIssue(newIssue);
        ResponseIssueDto responseIssueDto = ResponseIssueDto.of(newIssue);

        String page = "1";
        String condition = "is%3Aopen";
        PageRequest pageRequest = CustomPageable.defaultPage(page);
        String decode = URLDecoder.decode(condition, StandardCharsets.UTF_8);
        Page<ResponseIssueDto> responseIssueDtoPage = new PageImpl<>(List.of(responseIssueDto), pageRequest, 1);
        ResponseReadAllIssueDto responseReadAllIssueDto = ResponseReadAllIssueDto.of(1, 1, responseIssueDtoPage);
        given(issueService.findAllIssuesByCondition(decode, pageRequest)).willReturn(responseReadAllIssueDto);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/issues")
                        .queryParam("page", page)
                        .queryParam("q", condition))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParameters(
                                        parameterWithName("page").description("페이징 처리를 위한 페이지"),
                                        parameterWithName("q").description("필터링을 위한 문구")
                                ),
                                responseFields(
                                        fieldWithPath("openIssueCount").type(JsonFieldType.NUMBER).description("필터링된 이슈중 오픈 이슈의 개수"),
                                        fieldWithPath("closedIssueCount").type(JsonFieldType.NUMBER).description("필터링된 이슈중 닫힌 이슈의 개수"),
                                        fieldWithPath("issues.[].id").type(JsonFieldType.NUMBER).description("조회된 이슈의 아이디"),
                                        fieldWithPath("issues.[].title").type(JsonFieldType.STRING).description("조회된 이슈의 제목"),
                                        fieldWithPath("issues.[].author").type(JsonFieldType.STRING).description("조회된 이슈의 작성자 이름"),
                                        fieldWithPath("issues.[].image").type(JsonFieldType.STRING).description("조회된 이슈의 작성자 프로필 URL"),
                                        fieldWithPath("issues.[].commentCount").type(JsonFieldType.NUMBER).description("조회된 이슈에 작성된 댓글 개수"),
                                        fieldWithPath("issues.[].content").type(JsonFieldType.STRING).description("조회된 이슈의 내용"),
                                        fieldWithPath("issues.[].createdAt").type(JsonFieldType.NULL).description("조회된 이슈의 작성일"),
                                        fieldWithPath("issues.[].issueStatus").type(JsonFieldType.STRING).description("조회된 이슈의 상태"),
                                        fieldWithPath("issues.[].milestones").type(JsonFieldType.ARRAY).description("이슈에 등록된 마일스톤"),
                                        fieldWithPath("issues.[].milestones.[].id").type(JsonFieldType.NUMBER).description("해당 마일스톤 아이디"),
                                        fieldWithPath("issues.[].milestones.[].title").type(JsonFieldType.STRING).description("해당 마일스톤 제목"),
                                        fieldWithPath("issues.[].milestones.[].dueDate").type(JsonFieldType.STRING).description("해당 마일스톤 만료일"),
                                        fieldWithPath("issues.[].milestones.[].milestoneStatus").type(JsonFieldType.STRING).description("해당 마일스톤 상태"),
                                        fieldWithPath("issues.[].milestones.[].description").type(JsonFieldType.STRING).description("해당 마일스톤의 설명"),
                                        fieldWithPath("issues.[].milestones.[].openIssueCount").type(JsonFieldType.NUMBER).description("해당 마일스톤에 등록되어 있는 OPEN 상태의 이슈 개수"),
                                        fieldWithPath("issues.[].milestones.[].closeIssueCount").type(JsonFieldType.NUMBER).description("해당 마일스톤에 등록되어 있는 CLOSE 상태의 이슈 개수"),
                                        fieldWithPath("issues.[].comments").type(JsonFieldType.ARRAY).description("이슈에 등록된 댓글"),
                                        fieldWithPath("issues.[].labels").type(JsonFieldType.ARRAY).description("이슈에 등록된 레이블"),
                                        fieldWithPath("issues.[].labels.[].id").type(JsonFieldType.NUMBER).description("해당 레이블의 아이디"),
                                        fieldWithPath("issues.[].labels.[].title").type(JsonFieldType.STRING).description("해당 레이블의 제목"),
                                        fieldWithPath("issues.[].labels.[].description").type(JsonFieldType.STRING).description("해당 레이블의 설명"),
                                        fieldWithPath("issues.[].labels.[].backgroundColor").type(JsonFieldType.STRING).description("해당 레이블의 배경색"),
                                        fieldWithPath("issues.[].labels.[].textColor").type(JsonFieldType.STRING).description("해당 레이블의 색"),
                                        fieldWithPath("issues.[].assignees").type(JsonFieldType.ARRAY).description("이슈에 등록된 담당자"),
                                        fieldWithPath("issues.[].assignees.[].id").type(JsonFieldType.NUMBER).description("해당 담당자의 아이디"),
                                        fieldWithPath("issues.[].assignees.[].userId").type(JsonFieldType.STRING).description("해당 담당자의 닉네임"),
                                        fieldWithPath("issues.[].assignees.[].image").type(JsonFieldType.STRING).description("해당 담당자의 프로필 URL"),
                                        fieldWithPath("pageable.first").type(JsonFieldType.BOOLEAN).description("첫번째 페이지 유무"),
                                        fieldWithPath("pageable.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 유무"),
                                        fieldWithPath("pageable.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 유무"),
                                        fieldWithPath("pageable.totalPages").type(JsonFieldType.NUMBER).description("모든 페이지의 개수"),
                                        fieldWithPath("pageable.totalElements").type(JsonFieldType.NUMBER).description("모든 요소의 개수"),
                                        fieldWithPath("pageable.page").type(JsonFieldType.NUMBER).description("현재 페이지 위치"),
                                        fieldWithPath("pageable.size").type(JsonFieldType.NUMBER).description("현재 페이지에 나타나는 요소의 개수")
                                )
                        )
                );

        then(issueService).should().findAllIssuesByCondition(eq(decode), eq(pageRequest));
    }

    private Milestone getMilestone() {
        String milestoneTitle = "Milestone Title";
        LocalDate dueDate = LocalDate.now();
        String description = "Milestone description";

        return Milestone.of(1L, milestoneTitle, dueDate, description);
    }

    private Member getMember() {
        return Member.builder()
                .id(1L)
                .oauthId("oauthId")
                .email("member@email.com")
                .name("memberName")
                .profileImageUrl("profileUrl")
                .role(Role.GUEST)
                .build();
    }

    private List<Label> getLabels() {
        Label newLabel =
                Label.of(1L, "Label Title", "Label TextColor", "Label backColor", "Label Description");

        return List.of(newLabel);
    }

    private List<Assignee> getAssignees() {
        Assignee newAssignee =
                Assignee.of(1L, "profileUrl", "userId", "authorId");

        return List.of(newAssignee);
    }

    private RequestUpdateManyIssueStatus createRequestUpdateManyIssueStatusDto() {
        List<Long> issueIds = List.of(1L, 2L, 3L);
        IssueStatus issueStatus = IssueStatus.CLOSED;

        return RequestUpdateManyIssueStatus.of(issueIds, issueStatus);
    }

    private RequestUpdateIssueRelatedDto createRequestUpdateIssueRelatedDto() {
        Long relatedTypeId = 1L;

        return RequestUpdateIssueRelatedDto.from(relatedTypeId);
    }

    private RequestSaveIssueDto createRequestSaveIssueDto() {
        String issueTitle = "Issue Title";
        String issueContent = "Issue Content";
        List<Long> assigneeIds = List.of(1L);
        List<Long> labelIds = List.of(1L);
        List<Long> milestoneIds = List.of(1L);

        return RequestSaveIssueDto.of(issueTitle, issueContent, assigneeIds, labelIds, milestoneIds);
    }

    private RequestUpdateIssueTitleWithContentDto createRequestUpdateIssueTitleWithContentDto() {
        String issueTitle = "Update Issue Title";
        String issueContent = "Update Issue Content";

        return RequestUpdateIssueTitleWithContentDto.of(issueTitle, issueContent);
    }
}
