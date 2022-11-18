package team20.issuetracker.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueAssigneeRepository extends JpaRepository<IssueAssignee, Long> {
    @Query("select i from IssueAssignee i join fetch i.issue join fetch i.assignee")
    List<IssueAssignee> findAllAssignees();
}
