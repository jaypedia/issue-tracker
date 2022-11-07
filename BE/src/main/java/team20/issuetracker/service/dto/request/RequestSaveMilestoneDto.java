package team20.issuetracker.service.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestSaveMilestoneDto {

    @NotEmpty(message = "Milestone 의 제목은 공백일 수 없습니다.")
    @Size(max = 50, message = "Milestone 의 제목은 50글자를 넘을 수 없습니다.")
    private String title;

    @Size(max = 800, message = "Milestone 의 본문은 800글자를 넘을 수 없습니다.")
    private String description;

    private LocalDate dueDate;

    public static RequestSaveMilestoneDto of(String title, String description, LocalDate dueDate) {
        return new RequestSaveMilestoneDto(title, description, dueDate);
    }
}
