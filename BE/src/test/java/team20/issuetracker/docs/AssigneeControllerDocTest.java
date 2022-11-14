package team20.issuetracker.docs;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import team20.issuetracker.controller.AssigneeController;
import team20.issuetracker.docs.config.RestDocsConfig;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.service.AssigneeService;
import team20.issuetracker.service.dto.response.ResponseAssigneeDto;
import team20.issuetracker.service.dto.response.ResponseAssigneesDto;

@DisplayName("Spring Docs - 담당자")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(AssigneeController.class)
@Import(RestDocsConfig.class)
@ContextConfiguration(classes = {AssigneeController.class})
class AssigneeControllerDocTest {

    @Autowired
    private RestDocumentationResultHandler restDocs;
    @MockBean
    private AssigneeService assigneeService;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    public AssigneeControllerDocTest() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .uris()
                        .withScheme("https")
                        .withHost("api.rarus-be.com")
                        .withPort(443))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
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
        mockMvc.perform(get("/api/assignees"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("assignees.[].id").type(JsonFieldType.NUMBER).description("담당자 아이디"),
                                        fieldWithPath("assignees.[].userId").type(JsonFieldType.STRING).description("담당자 닉네임"),
                                        fieldWithPath("assignees.[].image").type(JsonFieldType.STRING).description("담당자 프로필 Url")
                                )
                        )
                );

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
