package team20.issuetracker.domain.assignee;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.issue.Issue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Assignee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String title;

    @OneToMany(mappedBy = "assignee")
    private List<Issue> issues = new ArrayList<>();

    @Builder
    public Assignee(String image, String title) {
        this.image = image;
        this.title = title;
    }
}
