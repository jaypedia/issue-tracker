package team20.issuetracker.domain.milestone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.milestone.request.SaveMilestoneDto;
import team20.issuetracker.domain.milestone.response.MilestoneDto;
import team20.issuetracker.domain.milestone.response.ReadAllMilestones;
import team20.issuetracker.service.MilestoneService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles( {"test"} )
@Transactional
@SpringBootTest
public class MilestoneReadTest {

    @Autowired
    MilestoneService milestoneService;

    @Test
    @DisplayName("생성한 Milestone 만큼의 List<Milestone> size 가 맞아야 한다.")
    void findAllMilestones() {
        // given
        SaveMilestoneDto saveMilestoneDtoA = new SaveMilestoneDto();
        saveMilestoneDtoA.setTitle("titleA");
        saveMilestoneDtoA.setDescription("descriptionA");
        saveMilestoneDtoA.setStartDate(LocalDateTime.now());
        saveMilestoneDtoA.setEndDate(LocalDateTime.now());

        SaveMilestoneDto saveMilestoneDtoB = new SaveMilestoneDto();
        saveMilestoneDtoA.setTitle("titleB");
        saveMilestoneDtoA.setDescription("descriptionB");
        saveMilestoneDtoA.setStartDate(LocalDateTime.now());
        saveMilestoneDtoA.setEndDate(LocalDateTime.now());

        milestoneService.save(saveMilestoneDtoA);
        milestoneService.save(saveMilestoneDtoB);

        // when
        List<MilestoneDto> milestoneDtos = milestoneService.findAll();
        ReadAllMilestones allMilestoneData = milestoneService.getAllMilestoneData(milestoneDtos);

        // then
        assertThat(allMilestoneData.getAllMilestoneCount()).isEqualTo(2);
    }
}
