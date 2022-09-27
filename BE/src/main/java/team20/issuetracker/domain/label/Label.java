package team20.issuetracker.domain.label;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.AuditingFields;
import team20.issuetracker.domain.issue.IssueLabel;
import team20.issuetracker.service.dto.request.RequestLabelDto;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Label extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 7)
    private String textColor;
    @Column(nullable = false, length = 7)
    private String backgroundColor;
    @Column(length = 100)
    private String description;
    @OneToMany(mappedBy = "label")
    public List<IssueLabel> issueLabels = new ArrayList<>();

    public static Label of(String title, String textColor, String backgroundColor, String description) {
        return new Label(null, title, textColor, backgroundColor, description, List.of());
    }

    public static Label of(Long id, String title, String textColor, String backgroundColor, String description) {
        return new Label(id, title, textColor, backgroundColor, description, List.of());
    }

    public void update(RequestLabelDto requestLabelDto) {
        this.title = requestLabelDto.getTitle();
        this.textColor = requestLabelDto.getTextColor();
        this.backgroundColor = requestLabelDto.getBackgroundColor();
        this.description = requestLabelDto.getDescription();
    }
}
