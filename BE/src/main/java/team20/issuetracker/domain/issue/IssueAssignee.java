package team20.issuetracker.domain.issue;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.assginee.Assignee;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IssueAssignee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private Assignee assignee;

    private IssueAssignee(Issue issue, Assignee assignee) {
        this.issue = issue;
        this.assignee = assignee;
    }

    public static IssueAssignee of(Issue issue, Assignee assignee) {
        return new IssueAssignee(issue, assignee);
    }
}
