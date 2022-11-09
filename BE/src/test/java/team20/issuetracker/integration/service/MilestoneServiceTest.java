package team20.issuetracker.integration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import team20.issuetracker.config.DatabaseCleanup;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.domain.milestone.MilestoneStatus;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[통합 테스트] 마일스톤 테스트")
@Transactional
@SpringBootTest
public class MilestoneServiceTest {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }

    @DisplayName("마일스톤 정보를 입력하면, 마일스톤을 저장한다.")
    @Test
    void 마일스톤_저장() {
        // given
        Milestone milestone = createMilestone();

        // when
        Milestone saveMilestone = milestoneRepository.save(milestone);

        // then
        assertThat(saveMilestone.getId()).isEqualTo(milestone.getId());
    }

    @DisplayName("마일스톤 ID 를 찾아 해당 마일스톤을 삭제한다.")
    @Test
    void 마일스톤_삭제() {
        // given
        Milestone milestone = createMilestone();
        Milestone saveMilestone = milestoneRepository.save(milestone);
        Milestone findMilestone = milestoneRepository.findById(saveMilestone.getId())
            .orElseThrow(() -> new NoSuchElementException("해당 Milestone 은 존재하지 않습니다."));

        // when
        issueRepository.deleteMilestone(findMilestone.getId());
        milestoneRepository.delete(findMilestone);
    }

    @DisplayName("마일스톤 ID 를 찾아 해당 마일스톤을 수정한다.")
    @Test
    void 마일스톤_수정() {
        // given
        Milestone milestone = createMilestone();
        Milestone saveMilestone = milestoneRepository.save(milestone);
        RequestUpdateMilestoneDto updateDto = createUpdateDto();

        Milestone findMilestone = milestoneRepository.findById(saveMilestone.getId())
            .orElseThrow(() -> new NoSuchElementException("해당 Milestone 은 존재하지 않습니다."));

        // when
        findMilestone.update(updateDto);

        // then
        assertThat(findMilestone.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(findMilestone.getDescription()).isEqualTo(updateDto.getDescription());
        assertThat(findMilestone.getDueDate()).isEqualTo(updateDto.getDueDate());
    }

    @DisplayName("저장된 모든 마일스톤 목록을 조회한다.")
    @Test
    void 마일스톤_목록_조회() {
        // given
        Milestone milestone = createMilestone();
        Milestone saveMilestone = milestoneRepository.save(milestone);

        // when
        List<Milestone> milestones = milestoneRepository.findAll();

        // then
        assertThat(milestones).hasSize(1);
        assertThat(milestones.get(0)).isEqualTo(saveMilestone);
    }

    @DisplayName("열려있는 모든 마일스톤을 조회한다.")
    @Test
    void 열린_마일스톤_목록_조회() {
        // given
        Milestone milestone = createMilestone();
        milestoneRepository.save(milestone);

        // when
        List<Milestone> openMilestones = milestoneRepository.findAll().stream()
            .filter(m -> m.getMilestoneStatus().toString().equals("OPEN"))
            .collect(Collectors.toList());

        // then
        assertThat(openMilestones).hasSize(1);
        assertThat(openMilestones.get(0).getMilestoneStatus()).isEqualTo(MilestoneStatus.OPEN);
    }

    @DisplayName("닫혀있는 모든 마일스톤을 조회한다.")
    @Test
    void 닫힌_마일스톤_목록_조회() {
        // given
        Milestone milestone = createClosedMilestone();
        milestoneRepository.save(milestone);

        // when
        List<Milestone> closedMilestones = milestoneRepository.findAll().stream()
            .filter(m -> m.getMilestoneStatus().toString().equals("CLOSED"))
            .collect(Collectors.toList());

        // then
        assertThat(closedMilestones).hasSize(1);
        assertThat(closedMilestones.get(0).getMilestoneStatus()).isEqualTo(MilestoneStatus.CLOSED);
    }

    @DisplayName("마일스톤 ID 에 해당하는 하나의 마일스톤을 조회한다.")
    @Test
    void 마일스톤_단일_조회() {
        // given
        Milestone milestone = createMilestone();
        Milestone saveMilestone = milestoneRepository.save(milestone);

        // when
        Milestone findMilestone = milestoneRepository.findById(saveMilestone.getId())
            .orElseThrow(() -> new CheckEntityException("해당 Milestone 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // then
        assertThat(findMilestone.getTitle()).isEqualTo(saveMilestone.getTitle());
        assertThat(findMilestone.getDescription()).isEqualTo(saveMilestone.getDescription());
        assertThat(findMilestone.getDueDate()).isEqualTo(saveMilestone.getDueDate());
    }

    @DisplayName("존재하지 않는 마일스톤 조회 시 예외가 발생해야 한다.")
    @Test
    void 마일스톤_단일_조회_실패() {
        // given
        Long milestoneId = 1L;

        // when & then
        assertThatThrownBy(() -> milestoneRepository.findById(2L)
            .orElseThrow(() -> new CheckEntityException("해당 Milestone 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST)))
            .isInstanceOf(CheckEntityException.class)
            .hasMessageContaining("해당 Milestone 은 존재하지 않습니다.");
    }

    private Milestone createMilestone() {
        String title = "Milestone Title";
        String description = "Milestone Description";
        LocalDate dueDate = LocalDate.now();
        RequestSaveMilestoneDto dto = RequestSaveMilestoneDto.of(title, description, dueDate);

        return Milestone.of(dto.getTitle(), dto.getDueDate(), dto.getDescription());
    }

    private Milestone createClosedMilestone() {
        String title = "Milestone Title";
        String description = "Milestone Description";
        LocalDate dueDate = LocalDate.now();
        MilestoneStatus milestoneStatus = MilestoneStatus.CLOSED;
        RequestSaveMilestoneDto dto = RequestSaveMilestoneDto.of(title, description, dueDate);

        return Milestone.of(dto.getTitle(), dto.getDueDate(), dto.getDescription(), milestoneStatus);
    }

    RequestUpdateMilestoneDto createUpdateDto() {
        String updateTitle = "Update Milestone Title";
        String updateDescription = "Update Milestone Description";
        LocalDate updateDueDate = LocalDate.now().plusDays(1);

        return RequestUpdateMilestoneDto.of(updateTitle, updateDescription, updateDueDate, MilestoneStatus.CLOSED);
    }
}
