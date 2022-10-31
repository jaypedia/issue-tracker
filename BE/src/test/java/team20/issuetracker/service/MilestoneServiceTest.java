package team20.issuetracker.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.domain.milestone.MilestoneStatus;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseReadAllMilestonesDto;

@DisplayName("비즈니스 로직 - 마일스톤")
@ExtendWith(MockitoExtension.class)
class MilestoneServiceTest {

    @InjectMocks
    private MilestoneService sut;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private IssueRepository issueRepository;

    @DisplayName("마일스톤을 조회하면, 모든 마일스톤을 출력한다.")
    @Test
    void 마일스톤_전체조회() throws Exception {
        //given
        RequestSaveMilestoneDto requestSaveMilestoneDto = createRequestSaveMilestoneDto();
        Milestone milestone = createMilestone(requestSaveMilestoneDto);
        given(milestoneRepository.findAll()).willReturn(List.of(milestone));

        //when
        ResponseReadAllMilestonesDto responseReadAllMilestonesDto = sut.findAll();

        //then
        assertThat(responseReadAllMilestonesDto.getMilestones())
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("title", requestSaveMilestoneDto.getTitle());

        then(milestoneRepository).should().findAll();
    }

    @DisplayName("마일스톤 정보를 입력하면, 마일스톤을 저장한다.")
    @Test
    void 마일스톤_저장() throws Exception {
        //given
        RequestSaveMilestoneDto requestMilestoneDto = createRequestSaveMilestoneDto();
        Milestone milestone = createMilestone(requestMilestoneDto);
        given(milestoneRepository.save(any(Milestone.class))).willReturn(milestone);

        //when
        sut.save(requestMilestoneDto);

        //then
        then(milestoneRepository).should().save(any(Milestone.class));
    }

    @DisplayName("마일스톤 정보를 입력하면, 해당 마일스톤을 수정한다.")
    @Test
    void 마일스톤_수정() throws Exception {
        //given
        RequestSaveMilestoneDto requestSaveMilestoneDto = createRequestSaveMilestoneDto();
        Milestone milestone = createMilestone(requestSaveMilestoneDto);
        RequestUpdateMilestoneDto requestUpdateMilestoneDto = createRequestUpdateMilestoneDto();
        given(milestoneRepository.findById(milestone.getId())).willReturn(Optional.of(milestone));

        //when
        sut.update(milestone.getId(), requestUpdateMilestoneDto);

        //then
        assertThat(requestUpdateMilestoneDto.getMilestoneStatus())
                .isNotEqualTo(MilestoneStatus.OPEN)
                .isEqualTo(MilestoneStatus.CLOSED);

        then(milestoneRepository).should().findById(milestone.getId());
    }

    @DisplayName("수정할 마일스톤을 찾을 수 없다면 예외를 발생시킨다.")
    @Test
    void 마일스톤_수정_실패() throws Exception {
        // given
        Long wrongMilestoneId = 2L;
        RequestUpdateMilestoneDto updateRequestDto = createRequestUpdateMilestoneDto();

        given(milestoneRepository.findById(wrongMilestoneId)).willThrow(new CheckEntityException("해당 Milestone 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // when
        assertThatThrownBy(() -> sut.update(wrongMilestoneId, updateRequestDto)).isInstanceOf(CheckEntityException.class)
            .hasMessageContaining("해당 Milestone 은 존재하지 않습니다.");

        // then
        then(milestoneRepository).should().findById(wrongMilestoneId);
    }

    @DisplayName("삭제할 마일스톤을 찾을 수 없다면 예외를 발생시킨다.")
    @Test
    void 마일스톤_삭제_실패() throws Exception {
        // given
        Long wrongMilestoneId = 2L;
        given(milestoneRepository.findById(wrongMilestoneId)).willThrow(new CheckEntityException("해당 Milestone 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // when
        assertThatThrownBy(() -> sut.delete(wrongMilestoneId)).isInstanceOf(CheckEntityException.class)
            .hasMessageContaining("해당 Milestone 은 존재하지 않습니다.");

        // then
        then(milestoneRepository).should().findById(wrongMilestoneId);
    }

    @DisplayName("마일스톤 아이디를 입력하면, 해당 마일스톤을 삭제한다.")
    @Test
    void 마일스톤_삭제() throws Exception {
        //given
        RequestSaveMilestoneDto requestSaveMilestoneDto = createRequestSaveMilestoneDto();
        Milestone milestone = createMilestone(requestSaveMilestoneDto);
        given(milestoneRepository.findById(milestone.getId())).willReturn(Optional.of(milestone));
        willDoNothing().given(issueRepository).deleteMilestone(milestone.getId());

        //when
        sut.delete(milestone.getId());

        //then
        then(milestoneRepository).should().findById(milestone.getId());
        then(milestoneRepository).should().delete(milestone);
        then(issueRepository).should().deleteMilestone(milestone.getId());
    }

    @DisplayName("마일스톤 아이디를 입력하면, 해당 마일스톤을 데이터를 출력한다.")
    @Test
    void 마일스톤_상세조회() throws Exception {
        //given
        RequestSaveMilestoneDto requestSaveMilestoneDto = createRequestSaveMilestoneDto();
        Milestone milestone = createMilestone(requestSaveMilestoneDto);
        given(milestoneRepository.findById(milestone.getId())).willReturn(Optional.of(milestone));

        //when
        sut.detail(milestone.getId());

        //then
        then(milestoneRepository).should().findById(milestone.getId());
    }

    private Milestone createMilestone(RequestSaveMilestoneDto requestSaveMilestoneDto) {
        return Milestone.of(
                1L,
                requestSaveMilestoneDto.getTitle(),
                requestSaveMilestoneDto.getDueDate(),
                requestSaveMilestoneDto.getDescription()
        );
    }

    private RequestSaveMilestoneDto createRequestSaveMilestoneDto() {
        return RequestSaveMilestoneDto.of(
                "마일스톤 타이틀",
                "마일스톤 설명",
                LocalDate.now().plusDays(1)
        );
    }

    private RequestUpdateMilestoneDto createRequestUpdateMilestoneDto() {
        return RequestUpdateMilestoneDto.of(
                "마일스톤 타이틀",
                "마일스톤 설명",
                LocalDate.now(),
                MilestoneStatus.CLOSED
        );
    }
}
