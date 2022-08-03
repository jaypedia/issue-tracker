package team20.issuetracker.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueLabelRepository extends JpaRepository<IssueLabel, Long> {
    @Query("select i from IssueLabel i join fetch i.label")
    List<IssueLabel> findAllIssueLabels();

    @Query("select il from IssueLabel il join fetch il.label where il.issue.id = :issueId")
    List<IssueLabel> findAllById(@Param("issueId") Long id);
}
