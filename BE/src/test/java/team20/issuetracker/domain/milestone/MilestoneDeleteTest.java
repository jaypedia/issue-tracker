package team20.issuetracker.domain.milestone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.milestone.request.SaveMilestoneDto;
import team20.issuetracker.domain.milestone.response.MilestoneDto;
import team20.issuetracker.service.MilestoneService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles( {"test"} )
@Transactional
@SpringBootTest
public class MilestoneDeleteTest {

    @Autowired
    MilestoneService milestoneService;

    @Test
    @DisplayName("Milestone Save 이후 해당 Milestone 을 delete 한 뒤 전체를 조회했을 때 갯수가 하나 적어야 한다.")
    void deleteTest() {
        // given
        SaveMilestoneDto saveMilestoneDtoA = new SaveMilestoneDto();
        saveMilestoneDtoA.setTitle("titleA");
        saveMilestoneDtoA.setDescription("descriptionA");
        saveMilestoneDtoA.setStartDate(LocalDateTime.now());
        saveMilestoneDtoA.setEndDate(LocalDateTime.now());

        SaveMilestoneDto saveMilestoneDtoB = new SaveMilestoneDto();
        saveMilestoneDtoB.setTitle("titleB");
        saveMilestoneDtoB.setDescription("descriptionB");
        saveMilestoneDtoB.setStartDate(LocalDateTime.now());
        saveMilestoneDtoB.setEndDate(LocalDateTime.now());

        Long saveMilestoneA = milestoneService.save(saveMilestoneDtoA);
        Long saveMilestoneB = milestoneService.save(saveMilestoneDtoB);

        // when
        milestoneService.delete(saveMilestoneA);
        List<MilestoneDto> findMilestoneDtos = milestoneService.findAll();

        // then
        assertThat(findMilestoneDtos.size()).isEqualTo(1);
    }
}
