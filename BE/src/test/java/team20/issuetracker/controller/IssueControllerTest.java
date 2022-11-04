package team20.issuetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import team20.issuetracker.controller.page.CustomPageable;
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("컨트롤러 - 이슈")
@WebMvcTest(IssueController.class)
@ContextConfiguration(classes = IssueController.class)
class IssueControllerTest {

    private final MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private IssueService issueService;

    public IssueControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[Controller][POST] 새로운 이슈 작성 - 정상 호출")
    @Test
    void 이슈_작성() throws Exception {
        // given
        RequestSaveIssueDto requestSaveIssueDto = createRequestSaveIssueDto();
        given(issueService.save(any(RequestSaveIssueDto.class))).willReturn(1L);

        // when & then
        mvc.perform(post("/api/issues")
                .content(mapper.writeValueAsString(requestSaveIssueDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(1L)));

        then(issueService).should().save(any(RequestSaveIssueDto.class));
    }

    @DisplayName("[Controller][DELETE] 이슈 삭제 - 정상 호출")
    @Test
    void 이슈_삭제() throws Exception {
        // given
        Long issueId = 1L;
        willDoNothing().given(issueService).delete(anyLong());

        // when & then
        mvc.perform(delete("/api/issues/" + issueId))
            .andDo(print())
            .andExpect(status().isOk());

        then(issueService).should().delete(anyLong());
    }

    @DisplayName("[Controller][POST] 이슈 제목 및 내용 수정 - 정상 호출")
    @Test
    void updateTitleWithContent() throws Exception {
        // given
        Long issueId = 1L;
        RequestUpdateIssueTitleWithContentDto requestUpdateIssueTitleWithContentDto = createRequestUpdateIssueTitleWithContentDto();
        given(issueService.updateTitleWithContent(eq(issueId), any(RequestUpdateIssueTitleWithContentDto.class))).willReturn(1L);

        // when & then
        mvc.perform(post("/api/issues/" + issueId)
                .content(mapper.writeValueAsString(requestUpdateIssueTitleWithContentDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

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
        mvc.perform(post("/api/issues/" + issueId + "/" + updateType)
                .content(mapper.writeValueAsString(requestUpdateIssueRelatedDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

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
        mvc.perform(post("/api/issues/" + issueId + "/" + updateType)
                .content(mapper.writeValueAsString(requestUpdateIssueRelatedDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

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
        mvc.perform(post("/api/issues/" + issueId + "/" + updateType)
                .content(mapper.writeValueAsString(requestUpdateIssueRelatedDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

        then(issueService).should().updateIssueRelated(eq(issueId), eq(updateType), any(RequestUpdateIssueRelatedDto.class));
    }

    @DisplayName("[Controller][POST] 단일 또는 다수의 이슈 수정 - 정상 호출")
    @Test
    void 이슈_단일_또는_다수_수정() throws Exception {
        // given
        RequestUpdateManyIssueStatus requestUpdateManyIssueStatusDto = createRequestUpdateManyIssueStatusDto();
        willDoNothing().given(issueService).updateManyIssueStatus(any(RequestUpdateManyIssueStatus.class));

        // when & then
        mvc.perform(post("/api/issues/action")
                .content(mapper.writeValueAsString(requestUpdateManyIssueStatusDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

        then(issueService).should().updateManyIssueStatus(any(RequestUpdateManyIssueStatus.class));
    }

    @DisplayName("[Controller][GET] 이슈 상세조회 - 정상 호출")
    @Test
    void 이슈_상세조회() throws Exception {
        // given
        Long issueId = 1L;
        RequestSaveIssueDto newRequestDto = getRequestSaveIssueDto();
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
        mapper.registerModule(new JavaTimeModule());
        mvc.perform(get("/api/issues/" + issueId)
                .content(mapper.writeValueAsString(responseIssueDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

        then(issueService).should().detail(issueId);
    }

    @DisplayName("[Controller][GET] 이슈 필터조회 - 정상 호출")
    @Test
    void readIssuesByCondition() throws Exception {
        // given
        String page = "1";
        String condition = "is%3Aopen";
        PageRequest pageRequest = CustomPageable.defaultPage(page);
        String decode = URLDecoder.decode(condition, StandardCharsets.UTF_8);
        given(issueService.findAllIssuesByCondition(eq(decode), eq(pageRequest))).willReturn(eq(any(ResponseReadAllIssueDto.class)));

        // when & then
        mvc.perform(get("/api/issues").queryParam("q", condition))
            .andDo(print())
            .andExpect(status().isOk());

        then(issueService).should().findAllIssuesByCondition(eq(decode), eq(pageRequest));
    }

    private RequestUpdateManyIssueStatus createRequestUpdateManyIssueStatusDto() {
        List<Long> issueIds = List.of(1L);
        IssueStatus issueStatus = IssueStatus.CLOSED;

        return RequestUpdateManyIssueStatus.of(issueIds, issueStatus);
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
        String issueTitle = "Issue Title";
        String issueContent = "Issue Content";

        return RequestUpdateIssueTitleWithContentDto.of(issueTitle, issueContent);
    }

    private RequestUpdateIssueRelatedDto createRequestUpdateIssueRelatedDto() {
        Long relatedTypeId = 1L;

        return RequestUpdateIssueRelatedDto.from(relatedTypeId);
    }

    private RequestSaveIssueDto getRequestSaveIssueDto() {
        String issueTitle = "Issue Title";
        String content = "Issue Content";
        List<Long> assigneeIds = List.of(1L);
        List<Long> labelIds = List.of(1L);
        List<Long> milestoneIds = List.of(1L);

        return RequestSaveIssueDto.of(issueTitle, content, assigneeIds, labelIds, milestoneIds);
    }

    private Milestone getMilestone() {
        String milestoneTitle = "Milestone Title";
        LocalDate dueDate = LocalDate.now();
        String description = "Milestone description";

        return Milestone.of(milestoneTitle, dueDate, description);
    }

    private Member getMember() {
        return Member.builder()
            .id(1L)
            .oauthId("78953393")
            .email("shoy1415@gmail.com")
            .name("geombong")
            .profileImageUrl("https://avatars.githubusercontent.com/u/78953393?v=4")
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
            Assignee.of(1L, "https://avatars.githubusercontent.com/u/78953393?v=4", "geombong", "78953393");

        return List.of(newAssignee);
    }
}