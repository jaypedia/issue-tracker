package team20.issuetracker.domain.assginee;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "assignee")
    public List<IssueAssignee> issueAssignees = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String userId;
    private String authorId;

    public Assignee(Long id, String image, String userId, String authorId) {
        this.id = id;
        this.image = image;
        this.userId = userId;
        this.authorId = authorId;
    }

    public Assignee(String image, String userId, String authorId) {
        this.image = image;
        this.userId = userId;
        this.authorId = authorId;
    }

    public static Assignee of(Long id, String image, String userId, String authorId) {
        return new Assignee(id, image, userId, authorId);
    }

    public static Assignee of(String image, String userId, String authorId) {
        return new Assignee(null, image, userId, authorId);
    }
}
