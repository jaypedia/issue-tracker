package team20.issuetracker.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import team20.issuetracker.config.DatabaseCleanup;
import team20.issuetracker.domain.issue.IssueStatus;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.login.oauth.Role;
import team20.issuetracker.service.AssigneeService;
import team20.issuetracker.service.IssueService;
import team20.issuetracker.service.LabelService;
import team20.issuetracker.service.MilestoneService;
import team20.issuetracker.service.dto.request.RequestLabelDto;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleWithContentDto;
import team20.issuetracker.service.dto.request.RequestUpdateManyIssueStatus;

@DisplayName("컨트롤러 - 레이블 통합 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class IssueControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public IssueControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private IssueService issueService;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private AssigneeService assigneeService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }

    @DisplayName("[Controller][POST] 이슈 작성 - 정상 호출")
    @Test
    void 이슈_저장() throws Exception {
        // given
        memberRepository.save(createMember());

        RequestSaveIssueDto saveIssueDto = saveIssueDto();

        // when & then
        mvc.perform(post("/api/issues")
                .header("Authorization", "Test Access Token")
                .content(mapper.writeValueAsString(saveIssueDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][GET] 이슈 필터조회 [열린 이슈 모두 조회] - 정상 호출")
    @Test
    void 이슈_필터조회_열린_이슈조회() throws Exception {
        // given
        String page = "1";
        String condition = "is%3Aopen";
        memberRepository.save(createMember());

        issueService.save(saveIssueDto());

        // when & then
        mvc.perform(get("/api/issues/" + page)
                .queryParam("q", condition)
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][GET] 이슈 필터조회 [닫힌 이슈 모두 조회] - 정상 호출")
    @Test
    void 이슈_필터조회_닫힌_이슈조회() throws Exception {
        // given
        String condition = "is%3Aclosed";
        memberRepository.save(createMember());

        issueService.save(saveIssueDto());

        // when & then
        mvc.perform(get("/api/issues").queryParam("q", condition)
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][GET] 이슈 단건조회 - 정상 호출")
    @Test
    void 이슈_단건_조회() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);

        RequestSaveIssueDto saveIssueDto = saveIssueDto();
        Long saveIssueId = issueService.save(saveIssueDto);

        // when & then
        mvc.perform(get("/api/issues/" + saveIssueId)
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][DELETE] 이슈 삭제 - 정상 호출")
    @Test
    void 이슈_삭제() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);

        RequestSaveIssueDto saveIssueDto = saveIssueDto();
        Long saveIssueId = issueService.save(saveIssueDto);

        // when & then
        mvc.perform(delete("/api/issues/" + saveIssueId)
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());
    }
    
    @DisplayName("[Controller][POST] 이슈 제목, 내용 수정 - 정상 호출")
    @Test
    void 이슈_제목_내용_수정() throws Exception {
        // given
        memberRepository.save(createMember());
        Long saveIssueId = issueService.save(saveIssueDto());

        RequestUpdateIssueTitleWithContentDto updateIssueDto = updateIssueTitleWithContentDto();

        // when % then
        mvc.perform(post("/api/issues/" + saveIssueId)
                .header("Authorization", "Test Access Token")
                .content(mapper.writeValueAsString(updateIssueDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][POST] 이슈 내부 마일스톤 수정 - 정상 호출")
    @Test
    void 이슈_연관된_마일스톤_수정() throws Exception {
        // given
        String updateType = "milestone";

        memberRepository.save(createMember());
        Long saveMilestoneId = milestoneService.save(createMilestoneDto());

        RequestSaveIssueDto saveIssueDto
            = RequestSaveIssueDto.of("Issue Title", "Issue Content", new ArrayList<>(), new ArrayList<>(), List.of(saveMilestoneId));

        Long saveIssueId = issueService.save(saveIssueDto);

        RequestUpdateIssueRelatedDto updateIssueRelatedDto = RequestUpdateIssueRelatedDto.from(saveMilestoneId);

        // when & then
        mapper.registerModule(new JavaTimeModule());
        mvc.perform(post("/api/issues/" + saveIssueId + "/" + updateType)
                .header("Authorization", "Test Access Token")
                .content(mapper.writeValueAsString(updateIssueRelatedDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][POST] 이슈 내부 레이블 수정 - 정상 호출")
    @Test
    void 이슈_연관된_레이블_수정() throws Exception {
        // given
        String updateType = "labels";

        memberRepository.save(createMember());
        Long saveLabelId = labelService.save(createLabelDto());

        RequestSaveIssueDto saveIssueDto
            = RequestSaveIssueDto.of("Issue Title", "Issue Content", new ArrayList<>(), List.of(saveLabelId), new ArrayList<>());

        Long saveIssueId = issueService.save(saveIssueDto);

        RequestUpdateIssueRelatedDto updateIssueRelatedDto = RequestUpdateIssueRelatedDto.from(saveLabelId);

        // when & then
        mapper.registerModule(new JavaTimeModule());
        mvc.perform(post("/api/issues/" + saveIssueId + "/" + updateType)
                .header("Authorization", "Test Access Token")
                .content(mapper.writeValueAsString(updateIssueRelatedDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][POST] 단일 또는 다수의 이슈 수정 - 정상 호출")
    @Test
    void 이슈_단일_또는_다수_수정() throws Exception {
        // given
        memberRepository.save(createMember());
        Long saveIssueId = issueService.save(saveIssueDto());

        RequestUpdateManyIssueStatus updateIssueStatus = RequestUpdateManyIssueStatus.of(List.of(saveIssueId), IssueStatus.CLOSED);

        // when & then
        mvc.perform(post("/api/issues/action")
                .header("Authorization", "Test Access Token")
                .content(mapper.writeValueAsString(updateIssueStatus))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    private Member createMember() {
        return Member.builder()
            .id(1L)
            .oauthId("oauthId")
            .name("geombong")
            .email("123@123.com")
            .profileImageUrl("profileUrl")
            .role(Role.GUEST)
            .build();
    }

    private RequestSaveIssueDto saveIssueDto() {
        String issueTitle = "Issue Title";
        String issueContent = "Issue Content";

        return RequestSaveIssueDto.of(issueTitle, issueContent, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private RequestUpdateIssueTitleWithContentDto updateIssueTitleWithContentDto() {
        String updateIssueTitle = "Update Issue Title";
        String updateIssueContent = "Update Issue Content";

        return RequestUpdateIssueTitleWithContentDto.of(updateIssueTitle, updateIssueContent);
    }

    RequestSaveMilestoneDto createMilestoneDto() {
        String title = "Milestone Title";
        String description = "Milestone Description";
        LocalDate dueDate = LocalDate.now();

        return RequestSaveMilestoneDto.of(title, description, dueDate);
    }

    RequestLabelDto createLabelDto() {
        String title = "Label Title";
        String backgroundColor = "#111111";
        String textColor = "#222222";
        String description = "Label Description";

        return RequestLabelDto.of(title, backgroundColor, textColor, description);
    }
}


