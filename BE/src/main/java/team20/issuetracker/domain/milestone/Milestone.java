package team20.issuetracker.domain.milestone;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.issue.Issue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;

    @OneToMany(mappedBy = "milestone")
    private List<Issue> issues = new ArrayList<>();
}
