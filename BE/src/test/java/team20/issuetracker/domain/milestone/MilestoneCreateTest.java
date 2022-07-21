package team20.issuetracker.domain.milestone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.MilestoneService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles( {"test"} )
@SpringBootTest
@Transactional
public class MilestoneCreateTest {

    @Autowired
    MilestoneService milestoneService;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Test
    @DisplayName("Issue 객체를 만들고 저장하면 저장된 Issue 의 ID 값이 반환되어야 한다.")
    void milestoneCreateTest() {
        // given
        RequestSaveMilestoneDto requestSaveMilestoneDto = new RequestSaveMilestoneDto();
        requestSaveMilestoneDto.setTitle("마일스톤 타이틀");
        requestSaveMilestoneDto.setDescription("마일스톤 설명");
        requestSaveMilestoneDto.setStartDate(LocalDateTime.now());
        requestSaveMilestoneDto.setEndDate(LocalDateTime.now());

        // when
        Milestone newMilestone = Milestone.of(requestSaveMilestoneDto.getTitle(), requestSaveMilestoneDto.getStartDate(), requestSaveMilestoneDto.getEndDate(), requestSaveMilestoneDto.getDescription());

        Long saveMilestoneId = milestoneRepository.save(newMilestone).getId();

        // then
        assertThat(saveMilestoneId).isEqualTo(1L);
    }

    @Test
    @DisplayName("Milestone 을 저장할 때, 클라이언트에서 올바르지 못한 값이 올 때는 예외가 발생해야 한다.")
    void milestoneCreateValidationTest() {
        String title = "";
        boolean exceptionCheck = false;

        for (int i = 0; i < 55; i++) {
            title += "1";
        }

        // given
        RequestSaveMilestoneDto requestSaveMilestoneDto = new RequestSaveMilestoneDto();
        requestSaveMilestoneDto.setTitle(title);
        requestSaveMilestoneDto.setDescription(null);
        requestSaveMilestoneDto.setStartDate(LocalDateTime.of(2020, 1, 1, 1, 1));
        requestSaveMilestoneDto.setEndDate(LocalDateTime.now());

        Milestone newMilestone = Milestone.of(requestSaveMilestoneDto.getTitle(), requestSaveMilestoneDto.getStartDate(), requestSaveMilestoneDto.getEndDate(), requestSaveMilestoneDto.getDescription());

        // when
        try {
            milestoneRepository.save(newMilestone);
        } catch (DataIntegrityViolationException exception) {
            exception.printStackTrace();
            exceptionCheck = true;
        }

        assertThat(exceptionCheck).isTrue();
    }
}
