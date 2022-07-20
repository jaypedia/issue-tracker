package team20.issuetracker.domain.issue;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import team20.issuetracker.service.IssueService;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;

@ActiveProfiles( {"test"} )
@SpringBootTest
@Transactional
class IssueCreateTest {

    @Autowired
    IssueService issueService;

    @Autowired
    IssueRepository issueRepository;

    @Test
    @DisplayName("Issue 객체를 만들고 저장하면 저장된 Issue 의 ID 값이 반환되어야 한다.")
    void issueCreateTest() {
        // given
        RequestSaveIssueDto requestSaveIssueDto = new RequestSaveIssueDto();
        requestSaveIssueDto.setAuthor("author");
        requestSaveIssueDto.setTitle("title");
        requestSaveIssueDto.setContent("content");
        requestSaveIssueDto.setCreatedAt(LocalDateTime.now());

        // when
        Issue newIssue = Issue.of(requestSaveIssueDto.getAuthor(), requestSaveIssueDto.getTitle(), requestSaveIssueDto.getContent(), requestSaveIssueDto.getCreatedAt(), null);

        Long saveIssueId = issueRepository.save(newIssue).getId();

        // then
        assertThat(saveIssueId).isEqualTo(1L);

    }
}
