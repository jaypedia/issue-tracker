package team20.issuetracker.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneRepository;
import team20.issuetracker.domain.milestone.MilestoneStatus;
import team20.issuetracker.service.dto.request.RequestSaveMilestoneDto;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;
import team20.issuetracker.service.dto.response.ResponseMilestoneDto;

@DisplayName("비즈니스 로직 - 마일스톤")
@ExtendWith(MockitoExtension.class)
class MilestoneServiceTest {

    @InjectMocks
    private MilestoneService sut;

    @Mock
    private MilestoneRepository milestoneRepository;

    @DisplayName("마일스톤을 조회하면, 모든 마일스톤을 출력한다.")
    @Test
    void given_when_then() throws Exception {
        //given
        RequestSaveMilestoneDto requestSaveMilestoneDto = createRequestSaveMilestoneDto();
        Milestone milestone = createMilestone(requestSaveMilestoneDto);
        given(milestoneRepository.findAll()).willReturn(List.of(milestone));

        //when
        List<ResponseMilestoneDto> responseMilestoneDtos = sut.findAll();

        //then
        assertThat(responseMilestoneDtos)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("title", responseMilestoneDtos.get(0).getTitle());
        then(milestoneRepository).should().findAll();
    }

    @DisplayName("마일스톤 정보를 입력하면, 마일스톤을 저장한다.")
    @Test
    void givenMilestoneInfo_whenSavingMilestone_thenSavedMilestone() throws Exception {
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
    void givenMilestoneInfo_whenUpdatingMilestone_thenUpdatedMilestone() throws Exception {
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

    @DisplayName("마일스톤 아이디를 입력하면, 해당 마일스톤을 삭제한다.")
    @Test
    void givenMilestoneId_whenDeletingMilestone_thenDeletedMilestone() throws Exception {
        //given
        RequestSaveMilestoneDto requestSaveMilestoneDto = createRequestSaveMilestoneDto();
        Milestone milestone = createMilestone(requestSaveMilestoneDto);
        given(milestoneRepository.findById(milestone.getId())).willReturn(Optional.of(milestone));

        //when
        sut.delete(milestone.getId());

        //then
        then(milestoneRepository).should().findById(milestone.getId());
        then(milestoneRepository).should().delete(milestone);
    }

    @DisplayName("마일스톤 아이디를 입력하면, 해당 마일스톤을 데이터를 출력한다.")
    @Test
    void givenMilestoneId_whenSearchingMilestone_thenReturnMilestone() throws Exception {
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
                requestSaveMilestoneDto.getStartDate(),
                requestSaveMilestoneDto.getEndDate(),
                requestSaveMilestoneDto.getDescription()
        );
    }

    private RequestSaveMilestoneDto createRequestSaveMilestoneDto() {
        return RequestSaveMilestoneDto.of(
                "마일스톤 타이틀",
                "마일스톤 설명",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );
    }

    private RequestUpdateMilestoneDto createRequestUpdateMilestoneDto() {
        return RequestUpdateMilestoneDto.of(
                "마일스톤 타이틀",
                "마일스톤 설명",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                MilestoneStatus.CLOSED
        );
    }
}
