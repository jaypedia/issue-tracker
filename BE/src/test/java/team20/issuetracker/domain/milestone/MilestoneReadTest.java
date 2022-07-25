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
//import team20.issuetracker.service.dto.response.ResponseReadAllMilestonesDto;
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
//public class MilestoneReadTest {
//
//    @Autowired
//    MilestoneService milestoneService;
//
//    @Test
//    @DisplayName("생성한 Milestone 만큼의 List<Milestone> size 가 맞아야 한다.")
//    void findAllMilestones() {
//        // given
//        RequestSaveMilestoneDto requestSaveMilestoneDtoA = new RequestSaveMilestoneDto();
//        requestSaveMilestoneDtoA.setTitle("titleA");
//        requestSaveMilestoneDtoA.setDescription("descriptionA");
//        requestSaveMilestoneDtoA.setStartDate(LocalDateTime.now());
//        requestSaveMilestoneDtoA.setEndDate(LocalDateTime.now());
//
//        RequestSaveMilestoneDto requestSaveMilestoneDtoB = new RequestSaveMilestoneDto();
//        requestSaveMilestoneDtoA.setTitle("titleB");
//        requestSaveMilestoneDtoA.setDescription("descriptionB");
//        requestSaveMilestoneDtoA.setStartDate(LocalDateTime.now());
//        requestSaveMilestoneDtoA.setEndDate(LocalDateTime.now());
//
//        milestoneService.save(requestSaveMilestoneDtoA, accessToken);
//        milestoneService.save(requestSaveMilestoneDtoB, accessToken);
//
//        // when
//        List<ResponseMilestoneDto> responseMilestoneDtos = milestoneService.findAll(jwt);
//        ResponseReadAllMilestonesDto allMilestoneData = milestoneService.getAllMilestoneData(responseMilestoneDtos);
//
//        // then
//        assertThat(allMilestoneData.getAllMilestoneCount()).isEqualTo(2);
//    }
//}
