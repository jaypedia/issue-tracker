package team20.issuetracker.domain.issue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.assignee.Assignee;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.label.Label;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class IssueRepositoryTest {

    @Autowired
    IssueRepository issueRepository;

    @AfterEach
    void cleanup() {
        issueRepository.deleteAll();
    }

    @Test
    @DisplayName("이슈가 저장되고 1L 이 반환되어야 한다.")
    void issueSaveTest() {

        // given
        Assignee assignee1 = Assignee.builder()
                .title("testAssigneeTitle1")
                .image("testImage1")
                .build();

        Assignee assignee2 = Assignee.builder()
                .title("testAssigneeTitle2")
                .image("testImage2")
                .build();

        Label label1 = Label.builder()
                .title("testLabelTitle1")
                .backgroundColor("black")
                .description("테스트 레이블 1")
                .textColor("white")
                .build();

        Label label2 = Label.builder()
                .title("testLabelTitle2")
                .backgroundColor("red")
                .description("테스트 레이블 2")
                .textColor("blue")
                .build();

        Comment comment1 = Comment.builder()
                .content("testComment1")
                .createdAt(LocalDateTime.now())
                .build();

        Comment comment2 = Comment.builder()
                .content("testComment2")
                .createdAt(LocalDateTime.now())
                .build();

        List<Assignee> assignees = new ArrayList<>();
        assignees.add(assignee1);
        assignees.add(assignee2);

        List<Label> labels = new ArrayList<>();
        labels.add(label1);
        labels.add(label2);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        SaveIssueDto saveIssueDto = SaveIssueDto.builder()
                .title("testIssueTitle")
                .content("testIssueContent")
                .assignees(assignees)
                .labels(labels)
                .milestone(null)
                .author("testAuthor")
                .issueStatus(IssueStatus.OPEN)
                .comments(comments)
                .build();

        // when
        Long saveIssueId = issueRepository.save(saveIssueDto.toEntity()).getId();

        // then
        assertThat(saveIssueId).isEqualTo(1);
    }
}