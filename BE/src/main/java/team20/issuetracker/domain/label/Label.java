package team20.issuetracker.domain.label;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String textColor;
    private String backgroundColor;
    private String description;

    @Builder
    public Label(String title, String textColor, String backgroundColor, String description) {
        this.title = title;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.description = description;
    }
}
