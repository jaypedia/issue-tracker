package team20.issuetracker.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueAssigneeRepository extends JpaRepository<IssueAssignee, Long> {
    @Query("select i from IssueAssignee i join fetch i.assignee")
    List<IssueAssignee> findAllTest();

    @Query("select ia from IssueAssignee ia join fetch ia.assignee where ia.issue.id = :issueId")
    List<IssueAssignee> findAllById(
            @Param("issueId") Long id);
}
