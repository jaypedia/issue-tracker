package team20.issuetracker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.login.oauth.Role;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 이")
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

    /**
     * String condition 검증
     * ?q=is:open |^& milestone:title |^& labels:title |^& assignees:id |^& me:id
     *
     */
    @Test
    void findAllIssuesByCondition() {
        // given


        // when


        // then
    }

    @Test
    void detail() {
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
        RequestSaveIssueDto newRequestDto = getFreeOfContentRequestSaveIssueDto();
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
        assertThat(newIssue.getIssueLabels()).hasSize(0);

        // then
        then(milestoneRepository).should().getReferenceById(newRequestDto.getMilestoneIds().get(0));
        then(assigneeRepository).should().findAllById(newRequestDto.getAssigneeIds());
        then(labelRepository).should().findAllById(newRequestDto.getLabelIds());
        then(memberRepository).should().findByOauthId(newIssue.getAuthorId());
        then(issueRepository).should().save(any(Issue.class));
    }

    @Test
    void delete() {
    }

    @Test
    void updateTitleWithContent() {
    }

    @Test
    void updateIssueRelated() {
    }

    @Test
    void updateManyIssueStatus() {
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