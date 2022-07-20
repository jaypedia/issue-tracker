package team20.issuetracker.domain.milestone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseMilestoneDto;
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
        RequestSaveMilestoneDto requestSaveMilestoneDtoA = new RequestSaveMilestoneDto();
        requestSaveMilestoneDtoA.setTitle("titleA");
        requestSaveMilestoneDtoA.setDescription("descriptionA");
        requestSaveMilestoneDtoA.setStartDate(LocalDateTime.now());
        requestSaveMilestoneDtoA.setEndDate(LocalDateTime.now());

        RequestUpdateMilestoneDto requestUpdateMilestoneDto = new RequestUpdateMilestoneDto();
        requestUpdateMilestoneDto.setTitle("제목 업데이트");
        requestUpdateMilestoneDto.setDescription("설명 업데이트");
        requestUpdateMilestoneDto.setStartDate(LocalDateTime.of(2022, 2, 2,12, 0,0));
        requestUpdateMilestoneDto.setStartDate(LocalDateTime.of(2022, 2, 22,12, 0,0));
        requestUpdateMilestoneDto.setMilestoneStatus(MilestoneStatus.CLOSE);

        Long saveMilestone = milestoneService.save(requestSaveMilestoneDtoA);
        ResponseMilestoneDto findMilestone = milestoneService.detail(saveMilestone);

        // when
        Long updateMilestoneId = milestoneService.update(findMilestone.getId(), requestUpdateMilestoneDto);

        // then
        assertThat(updateMilestoneId).isEqualTo(1);
    }

    @Test
    @DisplayName("최초 생성된 Milestone Title 과 Update 이후 Milestone Title 이 동일해야 한다.")
    void updateValueTest() {
        // given
        RequestSaveMilestoneDto requestSaveMilestoneDtoA = new RequestSaveMilestoneDto();
        requestSaveMilestoneDtoA.setTitle("titleA");
        requestSaveMilestoneDtoA.setDescription("descriptionA");
        requestSaveMilestoneDtoA.setStartDate(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        requestSaveMilestoneDtoA.setStartDate(LocalDateTime.of(2022, 1, 22, 0, 0, 0));

        RequestUpdateMilestoneDto requestUpdateMilestoneDto = new RequestUpdateMilestoneDto();
        requestUpdateMilestoneDto.setDescription("설명 업데이트");
        requestUpdateMilestoneDto.setStartDate(LocalDateTime.of(2022, 2, 2,12, 0,0));
        requestUpdateMilestoneDto.setStartDate(LocalDateTime.of(2022, 2, 22,12, 0,0));
        requestUpdateMilestoneDto.setMilestoneStatus(MilestoneStatus.CLOSE);

        Long saveMilestoneId = milestoneService.save(requestSaveMilestoneDtoA);
        ResponseMilestoneDto saveMilestone = milestoneService.detail(saveMilestoneId);

        // when
        Long updateMilestoneId = milestoneService.update(saveMilestone.getId(), requestUpdateMilestoneDto);
        ResponseMilestoneDto updateMilestone = milestoneService.detail(updateMilestoneId);

        // then
        assertThat(saveMilestone.getTitle()).isEqualTo(updateMilestone.getTitle());
        assertThat(saveMilestone.getDescription()).isNotEqualTo(updateMilestone.getDescription());
    }
}
