package team20.issuetracker.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueLabelRepository extends JpaRepository<IssueLabel, Long> {
}
