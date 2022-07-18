package team20.issuetracker.domain.issue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.config.WebConfig;
import team20.issuetracker.service.IssueService;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@Import(value = WebConfig.class) // Test 와 Main 의 최상위 디렉토리가 다르기 때문에 직접 넣어줘야 한다.
@SpringBootTest
@Transactional
class IssueCreateTest {

    @Autowired
    IssueService issueService;
    IssueRepository issueRepository;

    @AfterEach
    void cleanup() {
        issueRepository.deleteAll();
    }

    @Test
    @DisplayName("Issue 객체를 만들고 저장하면 저장된 Issue 의 ID 값이 반환되어야 한다.")
    void issueCreateTest() {
        // given
        SaveIssueDto saveIssueDto = new SaveIssueDto();
        saveIssueDto.setAuthor("author");
        saveIssueDto.setTitle("title");
        saveIssueDto.setContent("content");
        saveIssueDto.setCreatedAt(LocalDateTime.now());

        // when
        Issue newIssue = Issue.of(saveIssueDto.getAuthor(), saveIssueDto.getTitle(), saveIssueDto.getContent(), saveIssueDto.getCreatedAt(), null);
//        newIssue.addLabels(new ArrayList<>());
        newIssue.addAssignees(new ArrayList<>());

        Long saveIssueId = issueRepository.save(newIssue).getId();

        // then
        assertThat(saveIssueId).isEqualTo(1L);

    }
}
