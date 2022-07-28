package team20.issuetracker.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface IssueAssigneeRepository extends JpaRepository<IssueAssignee, Long> {
    @Query("select i from IssueAssignee i join fetch i.assignee")
    Set<IssueAssignee> findAllTest();
}
