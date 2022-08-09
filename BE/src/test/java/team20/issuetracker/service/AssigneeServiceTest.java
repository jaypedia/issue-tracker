package team20.issuetracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.service.dto.response.ResponseAssigneesDto;

@DisplayName("비즈니스 로직 - 담당자")
@ExtendWith(MockitoExtension.class)
class AssigneeServiceTest {

    @InjectMocks
    private AssigneeService sut;

    @Mock
    private AssigneeRepository assigneeRepository;

    @DisplayName("담당자를 조회하면, 모든 담당자를 출력한다.")
    @Test
    void givenFindAll_whenSearchingAssignees_thenReturnAssignees() throws Exception {
        //given
        Assignee assignee = createAssignee();
        given(assigneeRepository.findAll()).willReturn(List.of(assignee));

        //when
        ResponseAssigneesDto assigneesDto = sut.findAll();

        //then
        assertThat(assigneesDto.getAssignees())
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("title", assignee.getTitle());
        then(assigneeRepository).should().findAll();
    }

    private Assignee createAssignee() {
        return Assignee.of(
                1L,
                "담당자 이미지",
                "담당자 이름",
                "123456"
        );
    }
}
