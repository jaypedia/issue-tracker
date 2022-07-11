package team20.issuetracker.domain.milestone;

import team20.issuetracker.domain.issue.Issue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
