package team20.issuetracker.service.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseReadAllMilestonesDto {

    private int allMilestoneCount;
    private long openMilestoneCount;
    private long closeMilestoneCount;
    private List<ResponseMilestoneDto> milestones;

    public static ResponseReadAllMilestonesDto of(int allMilestoneCount, long openMilestonesCount, long closeMilestonesCount, List<ResponseMilestoneDto> milestones) {
        return new ResponseReadAllMilestonesDto(allMilestoneCount, openMilestonesCount, closeMilestonesCount, milestones);

    }
}
