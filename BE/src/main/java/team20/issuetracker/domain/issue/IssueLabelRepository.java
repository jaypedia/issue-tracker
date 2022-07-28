package team20.issuetracker.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface IssueLabelRepository extends JpaRepository<IssueLabel, Long> {
    @Query("select i from IssueLabel i join fetch i.label")
    Set<IssueLabel> findAllTest();
}
