package team20.issuetracker.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("select i from Issue i where i.title like concat('%', :title, '%')")
    List<Issue> findAllIssuesByTitle(@Param("title") String title);

    @Query("select i from Issue i where i.authorId = :oauthId")
    List<Issue> findAllMyIssues(@Param("oauthId") String oauthId);

    @Query("select i from Issue i join fetch i.member m join fetch i.milestone mi")
    List<Issue> findAllIssue();
}
