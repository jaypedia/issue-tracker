package team20.issuetracker.domain.label;


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
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String textColor;
    private String backgroundColor;
    private String description;

    @OneToMany(mappedBy = "label")
    private List<Issue> issues = new ArrayList<>();

    @Builder
    public Label(String title, String textColor, String backgroundColor, String description) {
        this.title = title;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.description = description;
    }
}
