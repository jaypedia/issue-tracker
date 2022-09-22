package team20.issuetracker.domain.assginee;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.issue.IssueAssignee;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Assignee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String userId;
    private String authorId;

    @OneToMany(mappedBy = "assignee")
    public Set<IssueAssignee> issueAssignees = new HashSet<>();

    public Assignee(Long id, String image, String userId, String authorId) {
        this.id = id;
        this.image = image;
        this.userId = userId;
        this.authorId = authorId;
    }

    public static Assignee of(Long id, String image, String userId, String authorId) {
        return new Assignee(id, image, userId, authorId);
    }
}
