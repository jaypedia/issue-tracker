package team20.issuetracker.domain.issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("select i from Issue i where i.title like concat('%', :title, '%')")
    List<Issue> findAllIssuesByTitle(@Param("title") String title);

    @Query("select i from Issue i where i.authorId = :oauthId")
    List<Issue> findAllMyIssues(@Param("oauthId") String oauthId);

    @Query("select count(i) from Issue i")
    long findByIssueCount();

    @Query(value = "select i from Issue i left join fetch i.member m left join fetch i.milestone mi where i.status = :status",
            countQuery = "select count(i) from Issue i left join i.member left join i.milestone where i.status = :status")
    Page<Issue> findIssues(Pageable pageable, @Param("status") IssueStatus status);
}
