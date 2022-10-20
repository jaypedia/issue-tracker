package team20.issuetracker.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueRepository extends JpaRepository<Issue, Long>, IssueRepositoryCustom {

    @Query("select count(i) from Issue i")
    long findByIssueCount();

    @Modifying(clearAutomatically = true)
    @Query("update Issue i set i.milestone = null where i.milestone.id = :id")
    void deleteMilestone(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Issue i set i.status = :status where i.id IN :ids")
    void updateManyIssueStatus(@Param("ids") List<Long> ids, @Param("status") IssueStatus status);
}
