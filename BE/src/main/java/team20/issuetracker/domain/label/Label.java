package team20.issuetracker.domain.label;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.issue.IssueLabel;

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
    private List<IssueLabel> labels = new ArrayList<>();
}
