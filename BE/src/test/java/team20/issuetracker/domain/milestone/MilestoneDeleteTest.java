//package team20.issuetracker.domain.milestone;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
//import team20.issuetracker.service.dto.response.ResponseMilestoneDto;
//import team20.issuetracker.service.MilestoneService;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ActiveProfiles( {"test"} )
//@Transactional
//@SpringBootTest
//public class MilestoneDeleteTest {
//
//    @Autowired
//    MilestoneService milestoneService;
//
//    @Test
//    @DisplayName("Milestone Save 이후 해당 Milestone 을 delete 한 뒤 전체를 조회했을 때 갯수가 하나 적어야 한다.")
//    void deleteTest() {
//        // given
//        RequestSaveMilestoneDto requestSaveMilestoneDtoA = new RequestSaveMilestoneDto();
//        requestSaveMilestoneDtoA.setTitle("titleA");
//        requestSaveMilestoneDtoA.setDescription("descriptionA");
//        requestSaveMilestoneDtoA.setStartDate(LocalDateTime.now());
//        requestSaveMilestoneDtoA.setEndDate(LocalDateTime.now());
//
//        RequestSaveMilestoneDto requestSaveMilestoneDtoB = new RequestSaveMilestoneDto();
//        requestSaveMilestoneDtoB.setTitle("titleB");
//        requestSaveMilestoneDtoB.setDescription("descriptionB");
//        requestSaveMilestoneDtoB.setStartDate(LocalDateTime.now());
//        requestSaveMilestoneDtoB.setEndDate(LocalDateTime.now());
//
//        Long saveMilestoneA = milestoneService.save(requestSaveMilestoneDtoA, accessToken);
//        Long saveMilestoneB = milestoneService.save(requestSaveMilestoneDtoB, accessToken);
//
//        // when
//        milestoneService.delete(saveMilestoneA);
//        List<ResponseMilestoneDto> findResponseMilestoneDtos = milestoneService.findAll(jwt);
//
//        // then
//        assertThat(findResponseMilestoneDtos.size()).isEqualTo(1);
//    }
//}
