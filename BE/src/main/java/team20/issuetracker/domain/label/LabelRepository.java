package team20.issuetracker.domain.label;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {

    List<Label> findAllByAuthorId(String authorId);
}
