package team20.issuetracker.integration.service;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import team20.issuetracker.config.DatabaseCleanup;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.issue.IssueStatus;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.exception.CheckUpdateTypeException;
import team20.issuetracker.service.RelatedUpdatable;
import team20.issuetracker.service.SimpleRelatedUpdateFactory;
import team20.issuetracker.service.UpdateType;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleWithContentDto;
import team20.issuetracker.service.dto.request.RequestUpdateManyIssueStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class IssueServiceTest {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private AssigneeRepository assigneeRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }

    @DisplayName("마일스톤이 존재한다면 마일스톤이 포함된 상태로 이슈가 저장된다.")
    @Test
    void 마일스톤이_있는_이슈_저장() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);

        // when
        Issue saveIssue = issueRepository.save(issue);

        // then
        Issue findIssue = issueRepository.findById(saveIssue.getId()).get();
        assertThat(saveIssue).isEqualTo(findIssue);
    }

    @DisplayName("마일스톤이 존재하지 않는다면 마일스톤이 없는 상태로 이슈가 저장된다.")
    @Test
    void 마일스톤이_없는_이슈_저장() {
        // given
        RequestSaveIssueDto requestDto = getFreeOfMilestoneRequestSaveIssueDto();

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), null);
        issue.addAssignees(assignees);
        issue.addLabels(labels);

        // when
        Issue saveIssue = issueRepository.save(issue);

        // then
        Issue findIssue = issueRepository.findById(saveIssue.getId()).get();
        assertThat(saveIssue).isEqualTo(findIssue);
    }

    @DisplayName("Issue Content 가 존재하지 않는다면 Content 가 공백 문자열인 상태로 이슈가 저장된다.")
    @Test
    void Issue_Content_없는_이슈_저장() {
        // given
        RequestSaveIssueDto requestDto = getFreeOfContentRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);

        // when
        Issue saveIssue = issueRepository.save(issue);

        // then
        Issue findIssue = issueRepository.findById(saveIssue.getId()).get();
        assertThat(saveIssue).isEqualTo(findIssue);
        assertThat(saveIssue.getContent()).isEqualTo("");
    }

    @DisplayName("Assignee List 가 존재하지 않는다면 담당자가 빈 리스트인 상태로 이슈가 저장된다.")
    @Test
    void Assignee_List_없는_이슈_저장() {
        // given
        RequestSaveIssueDto requestDto = getFreeOfAssigneesRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);

        // when
        Issue saveIssue = issueRepository.save(issue);

        // then
        Issue findIssue = issueRepository.findById(saveIssue.getId()).get();
        assertThat(saveIssue).isEqualTo(findIssue);
        assertThat(saveIssue.getIssueAssignees()).hasSize(0);
    }

    @DisplayName("Label List 가 존재하지 않는다면 레이블이 빈 리스트인 상태로 이슈가 저장된다.")
    @Test
    void Label_List_없는_이슈_저장() {
        // given
        RequestSaveIssueDto requestDto = getFreeOfLabelsRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);

        // when
        Issue saveIssue = issueRepository.save(issue);

        // then
        Issue findIssue = issueRepository.findById(saveIssue.getId()).get();
        assertThat(saveIssue).isEqualTo(findIssue);
        assertThat(saveIssue.getIssueLabels()).hasSize(0);
    }

    @DisplayName("이슈 ID 를 통해 해당 이슈를 삭제한다.")
    @Test
    void 이슈_삭제_성공() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);
        issueRepository.save(issue);

        // when
        issueRepository.delete(issue);
    }

    @DisplayName("존재하지 않는 이슈 ID 를 받았을 경우 예외가 발생한다.")
    @Test
    void 이슈_삭제_실패_없는_이슈_아이디() {
        // given
        Long wrongIssueId = 2L;

        // when
        assertThatThrownBy(() -> issueRepository.findById(wrongIssueId)
            .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST)))
            .isInstanceOf(CheckEntityException.class)
            .hasMessageContaining("해당 Issue 는 존재하지 않습니다.");
    }

    @DisplayName("UpdateType 에 존재하지 않는 Type 을 받았을 경우 예외가 발생한다.")
    @Test
    void 존재하지_않는_updateType() {
        // given
        String wrongUpdateType = "wrongUpdateType";

        // when & then
        assertThatThrownBy(() -> UpdateType.checkUpdateType(wrongUpdateType))
            .isInstanceOf(CheckUpdateTypeException.class)
            .hasMessageContaining("해당 UpdateType 은 존재하지 않습니다.");
    }

    @DisplayName("이슈에 존재하던 레이블과 중복되지 않는 레이블을 이슈 내의 레이블에 추가한다.")
    @Test
    void 이슈_레이블_추가_성공() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Label anotherLabel
            = Label.of(2L, "Another Label Title", "#333333", "#444444", "Another Label Description");
        labelRepository.save(anotherLabel);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);
        issueRepository.save(issue);

        UpdateType updateType = UpdateType.LABELS;
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto =
            RequestUpdateIssueRelatedDto.from(anotherLabel.getId());

        RelatedUpdatable relatedType =
            new SimpleRelatedUpdateFactory(milestoneRepository, labelRepository, assigneeRepository).getRelatedType(updateType);

        // when
        relatedType.updateRelatedType(requestUpdateIssueRelatedDto, updateType, issue);

        // then
        assertThat(issue.getIssueLabels().get(0).getLabel().getTitle()).isEqualTo("Label Title");
        assertThat(issue.getIssueLabels().get(1).getLabel().getTitle()).isEqualTo("Another Label Title");
    }

    @DisplayName("이슈에 존재하던 레이블과 중복되는 레이블이 선택되면 이슈 내의 레이블에서 삭제한다.")
    @Test
    void 중복_선택된_이슈_레이블_삭제_성공() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);
        issueRepository.save(issue);

        UpdateType updateType = UpdateType.LABELS;
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto =
            RequestUpdateIssueRelatedDto.from(label.getId());

        RelatedUpdatable relatedType =
            new SimpleRelatedUpdateFactory(milestoneRepository, labelRepository, assigneeRepository).getRelatedType(updateType);

        // when
        relatedType.updateRelatedType(requestUpdateIssueRelatedDto, updateType, issue);

        // then
        assertThat(issue.getIssueLabels()).isEmpty();
    }

    @DisplayName("존재하지 않는 레이블 추가 또는 삭제 시 예외가 발생한다.")
    @Test
    void 이슈_레이블_수정_실패() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);
        issueRepository.save(issue);

        Long wrongLabelId = 2L;
        UpdateType updateType = UpdateType.LABELS;
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto =
            RequestUpdateIssueRelatedDto.from(wrongLabelId);

        RelatedUpdatable relatedType =
            new SimpleRelatedUpdateFactory(milestoneRepository, labelRepository, assigneeRepository).getRelatedType(updateType);

        // when & then
        assertThatThrownBy(() -> relatedType.updateRelatedType(requestUpdateIssueRelatedDto, updateType, issue))
            .isInstanceOf(CheckEntityException.class)
            .hasMessageContaining("해당 Label 은 존재하지 않습니다.");
    }

    @DisplayName("이슈에 존재하던 담당자와 중복되지 않는 담당자를 이슈 내의 담당자에 추가한다.")
    @Test
    void 이슈_담당자_추가_성공() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        Assignee anotherAssignee = Assignee.of(2L, "Another Image", "juni8453", "79444040");
        assigneeRepository.save(anotherAssignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);
        issueRepository.save(issue);

        UpdateType updateType = UpdateType.ASSIGNEES;
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto =
            RequestUpdateIssueRelatedDto.from(anotherAssignee.getId());

        RelatedUpdatable relatedType =
            new SimpleRelatedUpdateFactory(milestoneRepository, labelRepository, assigneeRepository).getRelatedType(updateType);

        // when
        relatedType.updateRelatedType(requestUpdateIssueRelatedDto, updateType, issue);

        // then
        assertThat(issue.getIssueAssignees().get(0).getAssignee().getUserId()).isEqualTo("geombong");
        assertThat(issue.getIssueAssignees().get(1).getAssignee().getUserId()).isEqualTo("juni8453");
    }

    @DisplayName("이슈에 존재하던 담당자와과 중복되는 담당자가 선택되면 이슈 내의 담당자에서 삭제한다.")
    @Test
    void 중복_선택된_이슈_담당자_삭제_성공() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);
        issueRepository.save(issue);

        UpdateType updateType = UpdateType.ASSIGNEES;
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto =
            RequestUpdateIssueRelatedDto.from(label.getId());

        RelatedUpdatable relatedType =
            new SimpleRelatedUpdateFactory(milestoneRepository, labelRepository, assigneeRepository).getRelatedType(updateType);

        // when
        relatedType.updateRelatedType(requestUpdateIssueRelatedDto, updateType, issue);

        // then
        assertThat(issue.getIssueAssignees()).isEmpty();
    }

    @DisplayName("존재하지 않는 담당자 추가 또는 삭제 시 예외가 발생한다.")
    @Test
    void 이슈_담당자_수정_실패() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);
        issueRepository.save(issue);

        Long wrongAssigneeId = 2L;
        UpdateType updateType = UpdateType.ASSIGNEES;
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto =
            RequestUpdateIssueRelatedDto.from(wrongAssigneeId);

        RelatedUpdatable relatedType =
            new SimpleRelatedUpdateFactory(milestoneRepository, labelRepository, assigneeRepository).getRelatedType(updateType);

        // when & then
        assertThatThrownBy(() -> relatedType.updateRelatedType(requestUpdateIssueRelatedDto, updateType, issue))
            .isInstanceOf(CheckEntityException.class)
            .hasMessageContaining("해당 Assignee 는 존재하지 않습니다.");
    }

    @DisplayName("이슈에 존재하던 마일스톤과 중복되지 않는 마일스톤을 교체한다.")
    @Test
    void 이슈_마일스톤_교체_성공() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Milestone antherMilestone
            = Milestone.of(2L, "Another Title", LocalDate.now().plusDays(1), "Another Description");
        milestoneRepository.save(antherMilestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);
        issueRepository.save(issue);

        UpdateType updateType = UpdateType.MILESTONE;
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto =
            RequestUpdateIssueRelatedDto.from(antherMilestone.getId());

        RelatedUpdatable relatedType =
            new SimpleRelatedUpdateFactory(milestoneRepository, labelRepository, assigneeRepository).getRelatedType(updateType);

        // when
        relatedType.updateRelatedType(requestUpdateIssueRelatedDto, updateType, issue);

        // then
        assertThat(issue.getMilestone().getTitle()).isEqualTo("Another Title");
        assertThat(issue.getMilestone().getDescription()).isEqualTo("Another Description");
    }

    @DisplayName("이슈에 존재하던 마일스톤과 중복되는 마일스톤이 선택되면 이슈 내의 마일스톤에서 삭제한다.")
    @Test
    void 중복_선택된_이슈_마일스톤_삭제_성공() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);
        issueRepository.save(issue);

        UpdateType updateType = UpdateType.MILESTONE;
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto =
            RequestUpdateIssueRelatedDto.from(label.getId());

        RelatedUpdatable relatedType =
            new SimpleRelatedUpdateFactory(milestoneRepository, labelRepository, assigneeRepository).getRelatedType(updateType);

        // when
        relatedType.updateRelatedType(requestUpdateIssueRelatedDto, updateType, issue);

        // then
        assertThat(issue.getMilestone()).isNull();
    }

    @DisplayName("존재하지 않는 마일스톤 추가 또는 삭제 시 예외가 발생한다.")
    @Test
    void 이슈_마일스톤_수정_실패() {
        // given
        RequestSaveIssueDto requestDto = getRequestSaveIssueDto();

        Milestone milestone = getMilestone();
        milestoneRepository.save(milestone);

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), milestone);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        milestone.updateIssue(issue);
        issueRepository.save(issue);

        Long wrongMilestoneId = 2L;
        UpdateType updateType = UpdateType.MILESTONE;
        RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto =
            RequestUpdateIssueRelatedDto.from(wrongMilestoneId);

        RelatedUpdatable relatedType =
            new SimpleRelatedUpdateFactory(milestoneRepository, labelRepository, assigneeRepository).getRelatedType(updateType);

        // when & then
        assertThatThrownBy(() -> relatedType.updateRelatedType(requestUpdateIssueRelatedDto, updateType, issue))
            .isInstanceOf(CheckEntityException.class)
            .hasMessageContaining("해당 Milestone 은 존재하지 않습니다.");
    }

    @DisplayName("이슈 ID 에 해당하는 하나의 이슈를 조회한다.")
    @Test
    void 이슈_단일조회_성공() {
        // given
        Issue issue = createIssue();
        Issue saveIssue = issueRepository.save(issue);

        // when
        Issue findIssue = issueRepository.findById(issue.getId()).get();

        // then
        assertThat(findIssue).isEqualTo(saveIssue);
    }

    @DisplayName("존재하지 않는 이슈 ID 가 요청되면 예외가 발생한다.")
    @Test
    void 이슈_단일조회_실패() {
        // given
        Long wrongIssueId = 2L;

        // when & then
        assertThatThrownBy(() -> issueRepository.findById(wrongIssueId)
            .orElseThrow(() -> new CheckEntityException("해당 Issue 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST)))
            .isInstanceOf(CheckEntityException.class)
            .hasMessageContaining("해당 Issue 는 존재하지 않습니다.");
    }

    @DisplayName("이슈 제목과 내용을 수정한다.")
    @Test
    void 이슈_제목_내용_수정_성공() {
        // given
        Issue issue = createIssue();
        RequestUpdateIssueTitleWithContentDto updateDto = createUpdateIssueTitleWithContentDto();
        Issue saveIssue = issueRepository.save(issue);

        // when
        saveIssue.updateTitleWithContent(updateDto);

        // then
        assertThat(saveIssue.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(saveIssue.getContent()).isEqualTo(updateDto.getContent());
    }

    @DisplayName("단일 또는 다중으로 선택한 이슈를 한번에 닫힌 이슈로 수정한다.")
    @Test
    void 선택한_이슈_상태_수정() {
        // given
        Issue issue = createIssue();
        Issue saveIssue = issueRepository.save(issue);
        List<Long> issueIds = List.of(issue.getId());

        RequestUpdateManyIssueStatus updateDto = RequestUpdateManyIssueStatus.of(issueIds, IssueStatus.CLOSED);

        // when
        issueRepository.updateManyIssueStatus(updateDto.getIds(), updateDto.getIssueStatus());

        // then
        Issue findIssue = issueRepository.findById(saveIssue.getId()).get();
        assertThat(findIssue.getStatus()).isEqualTo(IssueStatus.CLOSED);
    }

    @DisplayName("지정한 필터에 맞춰 이슈 목록이 조회된다.")
    @Test
    void 이슈_필터_조회_성공() {
        //given
        RequestSaveIssueDto requestDto = getFreeOfMilestoneRequestSaveIssueDto();

        Label label = getLabel();
        labelRepository.save(label);

        Assignee assignee = getAssignee();
        assigneeRepository.save(assignee);

        List<Assignee> assignees = assigneeRepository.findAllById(requestDto.getAssigneeIds());
        List<Label> labels = labelRepository.findAllById(requestDto.getLabelIds());

        Issue issue = Issue.of(requestDto.getTitle(), requestDto.getContent(), null);
        issue.addAssignees(assignees);
        issue.addLabels(labels);
        issueRepository.save(issue);

        String condition = "is:open";
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.Direction.DESC, "id");
        MultiValueMap<String, String> conditionMap = new LinkedMultiValueMap<>();
        conditionMap.add(condition.split(":")[0], condition.split(":")[1]);

        // when
        Page<Issue> allIssuesByCondition = issueRepository.findAllIssuesByCondition(conditionMap, pageRequest);

        // then
        assertThat(allIssuesByCondition.getTotalElements()).isEqualTo(1);
        assertThat(allIssuesByCondition.getTotalPages()).isEqualTo(1);
    }

    private RequestUpdateIssueTitleWithContentDto createUpdateIssueTitleWithContentDto() {
        String updateIssueTitle = "Update Issue Title";
        String updateIssueContent = "Update Issue Content";

        return RequestUpdateIssueTitleWithContentDto.of(updateIssueTitle, updateIssueContent);
    }

    private Issue createIssue() {
        Long issueId = 1L;
        String issueTitle = "Issue Title";
        String issueContent = "Issue Content";
        return Issue.of(issueId, issueTitle, issueContent, null);
    }

    private RequestSaveIssueDto getRequestSaveIssueDto() {
        String issueTitle = "Issue Title";
        String issueContent = "Issue Content";
        List<Long> assigneeIds = List.of(1L);
        List<Long> labelIds = List.of(1L);
        List<Long> milestoneIds = List.of(1L);

        return RequestSaveIssueDto.of(issueTitle, issueContent, assigneeIds, labelIds, milestoneIds);
    }

    private RequestSaveIssueDto getFreeOfAssigneesRequestSaveIssueDto() {
        String issueTitle = "Issue Title";
        String issueContent = "Issue Content";
        List<Long> labelIds = List.of(1L);
        List<Long> milestoneIds = List.of(1L);

        return RequestSaveIssueDto.of(issueTitle, issueContent, new ArrayList<>(), labelIds, milestoneIds);
    }

    private RequestSaveIssueDto getFreeOfLabelsRequestSaveIssueDto() {
        String issueTitle = "Issue Title";
        String issueContent = "Issue Content";
        List<Long> milestoneIds = List.of(1L);

        return RequestSaveIssueDto.of(issueTitle, issueContent, new ArrayList<>(), new ArrayList<>(), milestoneIds);
    }

    private RequestSaveIssueDto getFreeOfMilestoneRequestSaveIssueDto() {
        String issueTitle = "Issue Title";
        String issueContent = "Issue Content";
        List<Long> assigneeIds = List.of(1L);
        List<Long> labelIds = List.of(1L);

        return RequestSaveIssueDto.of(issueTitle, issueContent, assigneeIds, labelIds, new ArrayList<>());
    }

    private RequestSaveIssueDto getFreeOfContentRequestSaveIssueDto() {
        String issueTitle = "Issue Title";
        String issueContent = "";
        List<Long> assigneeIds = List.of(1L);
        List<Long> labelIds = List.of(1L);
        List<Long> milestoneIds = List.of(1L);

        return RequestSaveIssueDto.of(issueTitle, issueContent, assigneeIds, labelIds, milestoneIds);
    }

    private Milestone getMilestone() {
        String milestoneTitle = "Milestone Title";
        LocalDate dueDate = LocalDate.now();
        String milestoneDescription = "Milestone description";

        return Milestone.of(milestoneTitle, dueDate, milestoneDescription);
    }

    private Label getLabel() {
        return Label.of("Label Title", "#23026B", "#23026B", "Label Description");
    }

    private Assignee getAssignee() {
        return Assignee.of("https://avatars.githubusercontent.com/u/78953393?v=4", "geombong", "78953393");
    }
}