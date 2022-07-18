package team20.issuetracker.domain.milestone;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private Milestone(String title, LocalDateTime startDate, LocalDateTime endDate, String description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public static Milestone of(String title, LocalDateTime startDate, LocalDateTime endDate, String description) {
        return new Milestone(title, startDate, endDate, description);
    }
}
