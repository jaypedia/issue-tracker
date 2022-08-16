package team20.issuetracker.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
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

    @NotEmpty
    @Size(max = 50, message = "Milestone 의 제목은 50글자를 넘을 수 없습니다.")
    private String title;

    @Size(max = 800, message = "Milestone 의 본문은 800글자를 넘을 수 없습니다.")
    private String description;

    @FutureOrPresent(message = "과거의 시간을 설정할 수는 없습니다.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    public static RequestSaveMilestoneDto of(String title, String description, LocalDate dueDate) {
        return new RequestSaveMilestoneDto(title, description, dueDate);
    }
}
