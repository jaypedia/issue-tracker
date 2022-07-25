package team20.issuetracker.domain.milestone;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    List<Milestone> findAllByAuthorId(String authorId);
}
