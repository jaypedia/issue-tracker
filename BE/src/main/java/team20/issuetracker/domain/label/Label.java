package team20.issuetracker.domain.label;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.issue.IssueLabel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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
    public List<IssueLabel> issueLabels = new ArrayList<>();


    public static Label of(String title, String textColor, String backgroundColor, String description) {
        return new Label(null, title, textColor, backgroundColor, description, List.of());
    }

    public static Label of(Long id, String title, String textColor, String backgroundColor, String description) {
        return new Label(id, title, textColor, backgroundColor, description, List.of());
    }

    public void update(UpdateLabelDto updateLabelDto) {
        this.title = updateLabelDto.getTitle() != null ? updateLabelDto.getTitle() : title;
        this.textColor = updateLabelDto.getTextColor() != null ? updateLabelDto.getTextColor() : textColor;
        this.backgroundColor = updateLabelDto.getBackgroundColor() != null ? updateLabelDto.getBackgroundColor() : backgroundColor;
        this.description = updateLabelDto.getDescription() != null ? updateLabelDto.getDescription() : description;
    }
}
