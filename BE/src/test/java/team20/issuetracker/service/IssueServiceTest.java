package team20.issuetracker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.issue.IssueStatus;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.login.oauth.Role;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleWithContentDto;
import team20.issuetracker.service.dto.request.RequestUpdateManyIssueStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 이슈")
@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

    @InjectMocks
    private IssueService sut;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private AssigneeRepository assigneeRepository;

    @Mock
    private LabelRepository labelRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void 이슈_필터_조회_성공() throws Exception {
        //given
        Long issueId = 1L;
        Member newMember = getMember();
        List<Assignee> assignees = getAssignees();
        List<Label> labels = getLabels();
        Issue newIssue = Issue.of(issueId, "title", "content", null);
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        newIssue.addMember(newMember);

        String condition = "is:open";
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.Direction.DESC, "createAt");
        MultiValueMap<String, String> conditionMap = new LinkedMultiValueMap<>();
        conditionMap.add(condition.split(":")[0], condition.split(":")[1]);
        Page<Issue> issuePage = new PageImpl<>(List.of(newIssue));

        given(issueRepository.findAllIssuesByCondition(conditionMap, pageRequest)).willReturn(issuePage);

        //when
        sut.findAllIssuesByCondition(condition, pageRequest);

        //then
        then(issueRepository).should().findAllIssuesByCondition(conditionMap, pageRequest);
    }

    @DisplayName("마일스톤이 존재한다면 마일스톤이 포함된 상태로 이슈가 저장된다.")
    @Test
    void 마일스톤이_있는_이슈_저장() {
        // given
        RequestSaveIssueDto newRequestDto = getRequestSaveIssueDto();
        Milestone newMilestone = getMilestone();
        Member newMember = getMember();
        List<Assignee> assignees = getAssignees();
        List<Label> labels = getLabels();

        Issue newIssue = Issue.of(newRequestDto.getTitle(), newRequestDto.getContent(), newMilestone);
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        newMilestone.updateIssue(newIssue);

        given(milestoneRepository.getReferenceById(newRequestDto.getMilestoneIds().get(0))).willReturn(newMilestone);
        given(assigneeRepository.findAllById(newRequestDto.getAssigneeIds())).willReturn(assignees);
        given(labelRepository.findAllById(newRequestDto.getLabelIds())).willReturn(labels);
        given(memberRepository.findByOauthId(newIssue.getAuthorId())).willReturn(Optional.of(newMember));
        given(issueRepository.save(any(Issue.class))).willReturn(newIssue);

        // when
        sut.save(newRequestDto);
        assertThat(newIssue.getMilestone()).isNotNull();

        // then
        then(milestoneRepository).should().getReferenceById(newRequestDto.getMilestoneIds().get(0));
        then(assigneeRepository).should().findAllById(newRequestDto.getAssigneeIds());
        then(labelRepository).should().findAllById(newRequestDto.getLabelIds());
        then(memberRepository).should().findByOauthId(newIssue.getAuthorId());
        then(issueRepository).should().save(any(Issue.class));
    }

    @DisplayName("마일스톤이 존재하지 않는다면 마일스톤이 없는 상태로 이슈가 저장된다.")
    @Test
    void 마일스톤이_존재하지_않는_이슈_저장() {
        // given
        RequestSaveIssueDto newRequestDto = getFreeOfMilestoneRequestSaveIssueDto();
        Member newMember = getMember();
        List<Assignee> assignees = getAssignees();
        List<Label> labels = getLabels();

        Issue newIssue = Issue.of(newRequestDto.getTitle(), newRequestDto.getContent(), null);
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);

        given(assigneeRepository.findAllById(newRequestDto.getAssigneeIds())).willReturn(assignees);
        given(labelRepository.findAllById(newRequestDto.getLabelIds())).willReturn(labels);
        given(memberRepository.findByOauthId(newIssue.getAuthorId())).willReturn(Optional.of(newMember));
        given(issueRepository.save(any(Issue.class))).willReturn(newIssue);

        // when
        sut.save(newRequestDto);
        assertThat(newIssue.getMilestone()).isNull();

        // then
        then(assigneeRepository).should().findAllById(newRequestDto.getAssigneeIds());
        then(labelRepository).should().findAllById(newRequestDto.getLabelIds());
        then(memberRepository).should().findByOauthId(newIssue.getAuthorId());
        then(issueRepository).should().save(any(Issue.class));
    }

    @DisplayName("Issue Content 가 존재하지 않는다면 Content 가 공백 문자열인 상태로 이슈가 저장된다.")
    @Test
    void Issue_Content_존재하지_않는_이슈_저장() {
        // given
        RequestSaveIssueDto newRequestDto = getFreeOfContentRequestSaveIssueDto();
        Milestone newMilestone = getMilestone();
        Member newMember = getMember();
        List<Assignee> assignees = getAssignees();
        List<Label> labels = getLabels();

        Issue newIssue = Issue.of(newRequestDto.getTitle(), newRequestDto.getContent(), newMilestone);
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        newMilestone.updateIssue(newIssue);

        given(milestoneRepository.getReferenceById(newRequestDto.getMilestoneIds().get(0))).willReturn(newMilestone);
        given(assigneeRepository.findAllById(newRequestDto.getAssigneeIds())).willReturn(assignees);
        given(labelRepository.findAllById(newRequestDto.getLabelIds())).willReturn(labels);
        given(memberRepository.findByOauthId(newIssue.getAuthorId())).willReturn(Optional.of(newMember));
        given(issueRepository.save(any(Issue.class))).willReturn(newIssue);

        // when
        sut.save(newRequestDto);
        assertThat(newIssue.getContent()).isEqualTo("");

        // then
        then(milestoneRepository).should().getReferenceById(newRequestDto.getMilestoneIds().get(0));
        then(assigneeRepository).should().findAllById(newRequestDto.getAssigneeIds());
        then(labelRepository).should().findAllById(newRequestDto.getLabelIds());
        then(memberRepository).should().findByOauthId(newIssue.getAuthorId());
        then(issueRepository).should().save(any(Issue.class));
    }

    @DisplayName("Assignee List 가 존재하지 않는다면 담당자가 빈 리스트인 상태로 이슈가 저장된다.")
    @Test
    void Assignee_List가_존재하지_않는_이슈_저장() {
        // given
        RequestSaveIssueDto newRequestDto = getRequestSaveIssueDto();
        Milestone newMilestone = getMilestone();
        Member newMember = getMember();
        List<Assignee> assignees = new ArrayList<>();
        List<Label> labels = getLabels();

        Issue newIssue = Issue.of(newRequestDto.getTitle(), newRequestDto.getContent(), newMilestone);
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        newMilestone.updateIssue(newIssue);

        given(milestoneRepository.getReferenceById(newRequestDto.getMilestoneIds().get(0))).willReturn(newMilestone);
        given(assigneeRepository.findAllById(newRequestDto.getAssigneeIds())).willReturn(assignees);
        given(labelRepository.findAllById(newRequestDto.getLabelIds())).willReturn(labels);
        given(memberRepository.findByOauthId(newIssue.getAuthorId())).willReturn(Optional.of(newMember));
        given(issueRepository.save(any(Issue.class))).willReturn(newIssue);

        // when
        sut.save(newRequestDto);
        assertThat(newIssue.getIssueAssignees()).hasSize(0);

        // then
        then(milestoneRepository).should().getReferenceById(newRequestDto.getMilestoneIds().get(0));
        then(assigneeRepository).should().findAllById(newRequestDto.getAssigneeIds());
        then(labelRepository).should().findAllById(newRequestDto.getLabelIds());
        then(memberRepository).should().findByOauthId(newIssue.getAuthorId());
        then(issueRepository).should().save(any(Issue.class));
    }

    @DisplayName("Label List 가 존재하지 않는다면 레이블이 빈 리스트인 상태로 이슈가 저장된다.")
    @Test
    void Label_List가_존재하지_않는_이슈_저장() {
        // given
        RequestSaveIssueDto newRequestDto = getFreeOfContentRequestSaveIssueDto();
        Milestone newMilestone = getMilestone();
        Member newMember = getMember();
        List<Assignee> assignees = getAssignees();
        List<Label> labels = new ArrayList<>();

        Issue newIssue = Issue.of(newRequestDto.getTitle(), newRequestDto.getContent(), newMilestone);
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        newMilestone.updateIssue(newIssue);

        given(milestoneRepository.getReferenceById(newRequestDto.getMilestoneIds().get(0))).willReturn(newMilestone);
        given(assigneeRepository.findAllById(newRequestDto.getAssigneeIds())).willReturn(assignees);
        given(labelRepository.findAllById(newRequestDto.getLabelIds())).willReturn(labels);
        given(memberRepository.findByOauthId(newIssue.getAuthorId())).willReturn(Optional.of(newMember));
        given(issueRepository.save(any(Issue.class))).willReturn(newIssue);

        // when
        sut.save(newRequestDto);
        assertThat(newIssue.getIssueLabels()).isEmpty();

        // then
        then(milestoneRepository).should().getReferenceById(newRequestDto.getMilestoneIds().get(0));
        then(assigneeRepository).should().findAllById(newRequestDto.getAssigneeIds());
        then(labelRepository).should().findAllById(newRequestDto.getLabelIds());
        then(memberRepository).should().findByOauthId(newIssue.getAuthorId());
        then(issueRepository).should().save(any(Issue.class));
    }

    @Test
    void 이슈_삭제_성공() throws Exception {
        //given
        Long issueId = 1L;
        Issue issue = Issue.of(issueId, "title", "content", null);

        given(issueRepository.findById(issueId)).willReturn(Optional.of(issue));
        willDoNothing().given(issueRepository).delete(issue);

        //when
        sut.delete(issueId);

        //then
        then(issueRepository).should().findById(issueId);
        then(issueRepository).should().delete(issue);
    }

    @Test
    void 이슈_삭제_실패_없는_이슈_아이디() throws Exception {
        //given
        Long wrongIssueId = 2L;

        given(issueRepository.findById(wrongIssueId)).willReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> {
            sut.delete(wrongIssueId);
        }).isInstanceOf(CheckEntityException.class)
                .hasMessageContaining("해당 Issue 는 존재하지 않습니다.");

        //then
        then(issueRepository).should().findById(wrongIssueId);
    }

    @Test
    void 이슈_제목_내용_수정_성공() throws Exception {
        //given
        Long issueId = 1L;
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        Issue issue = Issue.of(issueId, "title", "content", null);
        RequestUpdateIssueTitleWithContentDto request = RequestUpdateIssueTitleWithContentDto.of(updateTitle, updateContent);

        given(issueRepository.findById(issueId)).willReturn(Optional.of(issue));

        //when
        sut.updateTitleWithContent(issueId, request);

        //then
        then(issueRepository).should().findById(issueId);
    }

    @Test
    void 이슈_제목_내용_수정_실패_없는_이슈_아이디() throws Exception {
        //given
        Long wrongIssueId = 2L;
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        RequestUpdateIssueTitleWithContentDto request = RequestUpdateIssueTitleWithContentDto.of(updateTitle, updateContent);

        given(issueRepository.findById(wrongIssueId)).willReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> {
            sut.updateTitleWithContent(wrongIssueId, request);
        }).isInstanceOf(CheckEntityException.class)
                .hasMessageContaining("해당 Issue 는 존재하지 않습니다.");

        //then
        then(issueRepository).should().findById(wrongIssueId);
    }

    @Test
    void 이슈_레이블_수정_성공() throws Exception {
        //given
        Long issueId = 1L;
        Long labelId = 1L;
        String type = "labels";
        Issue issue = Issue.of(issueId, "title", "content", null);
        RequestUpdateIssueRelatedDto request = new RequestUpdateIssueRelatedDto(labelId);
        Label label = Label.of(request.getId(), "title", "textColor", "backgroundColor", "description");

        given(issueRepository.findById(issueId)).willReturn(Optional.of(issue));
        given(labelRepository.findById(request.getId())).willReturn(Optional.of(label));

        //when
        sut.updateIssueRelated(issueId, type, request);

        //then
        then(issueRepository).should().findById(issueId);
        then(labelRepository).should().findById(request.getId());
    }

    @Test
    void 이슈_레이블_수정_실패_없는_레이블_아이디() throws Exception {
        //given
        Long issueId = 1L;
        Long wrongLabelId = 2L;
        String type = "labels";
        Issue issue = Issue.of(issueId, "title", "content", null);
        RequestUpdateIssueRelatedDto request = new RequestUpdateIssueRelatedDto(wrongLabelId);

        given(issueRepository.findById(issueId)).willReturn(Optional.of(issue));
        given(labelRepository.findById(request.getId())).willReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> {
            sut.updateIssueRelated(issueId, type, request);
        }).isInstanceOf(CheckEntityException.class)
                .hasMessageContaining("해당 Label 은 존재하지 않습니다.");

        //then
        then(issueRepository).should().findById(issueId);
        then(labelRepository).should().findById(request.getId());
    }

    @Test
    void 이슈_마일스톤_수정_성공() throws Exception {
        //given
        Long issueId = 1L;
        Long milestoneId = 1L;
        String type = "milestone";
        Issue issue = Issue.of(issueId, "title", "content", null);
        RequestUpdateIssueRelatedDto request = new RequestUpdateIssueRelatedDto(milestoneId);
        Milestone milestone = Milestone.of(request.getId(), "milestoneTitle", LocalDate.now(), "description");

        given(issueRepository.findById(issueId)).willReturn(Optional.of(issue));
        given(milestoneRepository.findById(request.getId())).willReturn(Optional.of(milestone));

        //when
        sut.updateIssueRelated(issueId, type, request);

        //then
        then(issueRepository).should().findById(issueId);
        then(milestoneRepository).should().findById(request.getId());
    }

    @Test
    void 이슈_마일스톤_수정_실패_없는_마일스톤_아이디() throws Exception {
        //given
        Long issueId = 1L;
        Long wrongMilestoneId = 2L;
        String type = "milestone";
        Issue issue = Issue.of(issueId, "title", "content", null);
        RequestUpdateIssueRelatedDto request = new RequestUpdateIssueRelatedDto(wrongMilestoneId);

        given(issueRepository.findById(issueId)).willReturn(Optional.of(issue));
        given(milestoneRepository.findById(request.getId())).willReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> {
            sut.updateIssueRelated(issueId, type, request);
        }).isInstanceOf(CheckEntityException.class)
                .hasMessageContaining("해당 Milestone 은 존재하지 않습니다.");

        //then
        then(issueRepository).should().findById(issueId);
        then(milestoneRepository).should().findById(request.getId());
    }

    @Test
    void 이슈_담당자_수정_성공() throws Exception {
        //given
        Long issueId = 1L;
        Long assigneesId = 1L;
        String type = "assignees";
        Issue issue = Issue.of(issueId, "title", "content", null);
        RequestUpdateIssueRelatedDto request = new RequestUpdateIssueRelatedDto(issueId);
        Assignee assignee = Assignee.of(assigneesId, "imageUrl", "userId", "authorId");

        given(issueRepository.findById(issueId)).willReturn(Optional.of(issue));
        given(assigneeRepository.findById(request.getId())).willReturn(Optional.of(assignee));

        //when
        sut.updateIssueRelated(issueId, type, request);

        //then
        then(issueRepository).should().findById(issueId);
        then(assigneeRepository).should().findById(request.getId());
    }

    @Test
    void 이슈_담당자_수정_실패_없는_담당자_아이디() throws Exception {
        //given
        Long issueId = 1L;
        Long wrongAssigneesId = 2L;
        String type = "assignees";
        Issue issue = Issue.of(issueId, "title", "content", null);
        RequestUpdateIssueRelatedDto request = new RequestUpdateIssueRelatedDto(wrongAssigneesId);

        given(issueRepository.findById(issueId)).willReturn(Optional.of(issue));
        given(assigneeRepository.findById(request.getId())).willReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> {
            sut.updateIssueRelated(issueId, type, request);
        }).isInstanceOf(CheckEntityException.class)
                .hasMessageContaining("해당 Assignee 는 존재하지 않습니다.");

        //then
        then(issueRepository).should().findById(issueId);
        then(assigneeRepository).should().findById(request.getId());
    }

    @Test
    void 선택한_이슈_상태_수정_성공() throws Exception {
        //given
        List<Long> issueIds = List.of(1L, 2L);
        IssueStatus status = IssueStatus.OPEN;
        RequestUpdateManyIssueStatus request = RequestUpdateManyIssueStatus.of(issueIds, status);
        willDoNothing().given(issueRepository).updateManyIssueStatus(request.getIds(), request.getIssueStatus());

        //when
        sut.updateManyIssueStatus(request);

        //then
        then(issueRepository).should().updateManyIssueStatus(request.getIds(), request.getIssueStatus());
    }

    @Test
    void 이슈_상세조회_성공() throws Exception {
        //given
        Long issueId = 1L;
        Member newMember = getMember();
        List<Assignee> assignees = getAssignees();
        List<Label> labels = getLabels();
        Issue newIssue = Issue.of(issueId, "title", "content", null);
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        newIssue.addMember(newMember);

        given(issueRepository.findById(issueId)).willReturn(Optional.of(newIssue));

        //when
        sut.detail(issueId);

        //then
        then(issueRepository).should().findById(issueId);
    }

    @Test
    void 이슈_상세조회_실패() throws Exception {
        //given
        Long issueId = 1L;
        Long wrongId = 2L;
        Member newMember = getMember();
        List<Assignee> assignees = getAssignees();
        List<Label> labels = getLabels();
        Issue newIssue = Issue.of(issueId, "title", "content", null);
        newIssue.addAssignees(assignees);
        newIssue.addLabels(labels);
        newIssue.addMember(newMember);

        given(issueRepository.findById(wrongId)).willReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> {
            sut.detail(wrongId);
        }).isInstanceOf(CheckEntityException.class)
                .hasMessageContaining("해당 Issue 는 존재하지 않습니다");

        //then
        then(issueRepository).should().findById(wrongId);
    }

    private RequestSaveIssueDto getRequestSaveIssueDto() {
        String issueTitle = "Issue Title";
        String content = "Issue Content";
        List<Long> assigneeIds = List.of(1L);
        List<Long> labelIds = List.of(1L);
        List<Long> milestoneIds = List.of(1L);

        return RequestSaveIssueDto.of(issueTitle, content, assigneeIds, labelIds, milestoneIds);
    }

    private RequestSaveIssueDto getFreeOfMilestoneRequestSaveIssueDto() {
        String issueTitle = "Issue Title";
        String content = "Issue Content";
        List<Long> assigneeIds = List.of(1L);
        List<Long> labelIds = List.of(1L);

        return RequestSaveIssueDto.of(issueTitle, content, assigneeIds, labelIds, new ArrayList<>());
    }

    private RequestSaveIssueDto getFreeOfContentRequestSaveIssueDto() {
        String issueTitle = "Issue Title";
        String content = "";
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
