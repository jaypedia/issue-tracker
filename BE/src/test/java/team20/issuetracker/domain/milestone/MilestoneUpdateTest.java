package team20.issuetracker.domain.milestone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.milestone.request.SaveMilestoneDto;
import team20.issuetracker.domain.milestone.request.UpdateMilestoneDto;
import team20.issuetracker.domain.milestone.response.MilestoneDto;
import team20.issuetracker.service.MilestoneService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles( {"test"} )
@Transactional
@SpringBootTest
public class MilestoneUpdateTest {

    @Autowired
    MilestoneService milestoneService;

    @Test
    @DisplayName("Milestone 을 Update 하고 Update 한 해당 Milestone Id 가 리턴되어야 한다.")
    void updateIdTest() {
        // given
        SaveMilestoneDto saveMilestoneDtoA = new SaveMilestoneDto();
        saveMilestoneDtoA.setTitle("titleA");
        saveMilestoneDtoA.setDescription("descriptionA");
        saveMilestoneDtoA.setStartDate(LocalDateTime.now());
        saveMilestoneDtoA.setEndDate(LocalDateTime.now());

        UpdateMilestoneDto updateMilestoneDto = new UpdateMilestoneDto();
        updateMilestoneDto.setTitle("제목 업데이트");
        updateMilestoneDto.setDescription("설명 업데이트");
        updateMilestoneDto.setStartDate(LocalDateTime.of(2022, 2, 2,12, 0,0));
        updateMilestoneDto.setStartDate(LocalDateTime.of(2022, 2, 22,12, 0,0));
        updateMilestoneDto.setMilestoneStatus(MilestoneStatus.CLOSE);

        Long saveMilestone = milestoneService.save(saveMilestoneDtoA);
        MilestoneDto findMilestone = milestoneService.detail(saveMilestone);

        // when
        Long updateMilestoneId = milestoneService.update(findMilestone.getId(), updateMilestoneDto);

        // then
        assertThat(updateMilestoneId).isEqualTo(1);
    }

    @Test
    @DisplayName("최초 생성된 Milestone Title 과 Update 이후 Milestone Title 이 동일해야 한다.")
    void updateValueTest() {
        // given
        SaveMilestoneDto saveMilestoneDtoA = new SaveMilestoneDto();
        saveMilestoneDtoA.setTitle("titleA");
        saveMilestoneDtoA.setDescription("descriptionA");
        saveMilestoneDtoA.setStartDate(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        saveMilestoneDtoA.setStartDate(LocalDateTime.of(2022, 1, 22, 0, 0, 0));

        UpdateMilestoneDto updateMilestoneDto = new UpdateMilestoneDto();
        updateMilestoneDto.setDescription("설명 업데이트");
        updateMilestoneDto.setStartDate(LocalDateTime.of(2022, 2, 2,12, 0,0));
        updateMilestoneDto.setStartDate(LocalDateTime.of(2022, 2, 22,12, 0,0));
        updateMilestoneDto.setMilestoneStatus(MilestoneStatus.CLOSE);

        Long saveMilestoneId = milestoneService.save(saveMilestoneDtoA);
        MilestoneDto saveMilestone = milestoneService.detail(saveMilestoneId);

        // when
        Long updateMilestoneId = milestoneService.update(saveMilestone.getId(), updateMilestoneDto);
        MilestoneDto updateMilestone = milestoneService.detail(updateMilestoneId);

        // then
        assertThat(saveMilestone.getTitle()).isEqualTo(updateMilestone.getTitle());
        assertThat(saveMilestone.getDescription()).isNotEqualTo(updateMilestone.getDescription());
    }
}
