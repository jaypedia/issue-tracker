package team20.issuetracker.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.config.DatabaseCleanup;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.service.AssigneeService;
import team20.issuetracker.service.dto.response.ResponseAssigneeDto;
import team20.issuetracker.service.dto.response.ResponseAssigneesDto;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("컨트롤러 - 마일스톤 통합 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class AssigneeControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public AssigneeControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private AssigneeService assigneeService;

    @MockBean
    private AssigneeService assigneeServiceMock;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }

    @DisplayName("모든 레이블 API 호출 시 올바르지 않은 Access Token 을 받는다면 Status 로 302 Redirect 응답이 발생한다.")
    @Test
    void 리다이렉트_302_응답_발생() throws Exception {
        // when & then
        mvc.perform(get("/api/milestones")
                .header("Authorization", "None Access Token"))
            .andDo(print())
            .andExpect(status().is(302));
    }

    @DisplayName("[Controller][GET] 담당자 리스트 전체 조회 - 정상 호출(Mock 사용)")
    @Test
    void 모든_담당자_조회() throws Exception {
        // given
        Assignee assignee = createAssignee();
        ResponseAssigneeDto responseAssigneeDto = ResponseAssigneeDto.from(assignee);
        ResponseAssigneesDto response = ResponseAssigneesDto.from(List.of(responseAssigneeDto));
        given(assigneeService.findAll()).willReturn(response);

        // when & then
        mvc.perform(get("/api/assignees")
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());

        then(assigneeService).should().findAll();
    }

    private Assignee createAssignee() {
        return Assignee.of(
            1L,
            "imageURL",
            "userId",
            "authorId"
        );
    }
}