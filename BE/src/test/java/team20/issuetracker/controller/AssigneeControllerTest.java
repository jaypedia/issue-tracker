package team20.issuetracker.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.service.AssigneeService;
import team20.issuetracker.service.dto.response.ResponseAssigneeDto;
import team20.issuetracker.service.dto.response.ResponseAssigneesDto;


@DisplayName("컨트롤러 - 담당자")
@WebMvcTest(AssigneeController.class)
@ContextConfiguration(classes = AssigneeController.class)
class AssigneeControllerTest {

    private final MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private AssigneeService assigneeService;

    public AssigneeControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[Controller][GET] 담당자 전체 조회 - 정상 호출")
    @Test
    void 담담자_전체_조회_성공() throws Exception {
        //given
        Assignee assignee = createAssignee();
        ResponseAssigneeDto responseAssigneeDto = ResponseAssigneeDto.from(assignee);
        ResponseAssigneesDto response = ResponseAssigneesDto.from(List.of(responseAssigneeDto));

        given(assigneeService.findAll()).willReturn(response);

        //when & then
        mvc.perform(get("/api/assignees"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.assignees").value(IsNull.notNullValue()))
                .andExpect(jsonPath("$.assignees[0].id", is(responseAssigneeDto.getId().intValue())))
                .andExpect(jsonPath("$.assignees[0].userId", is(responseAssigneeDto.getUserId())));

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


