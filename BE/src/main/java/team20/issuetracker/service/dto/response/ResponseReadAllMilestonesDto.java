package team20.issuetracker.service.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseReadAllMilestonesDto {

    private int allMilestoneCount;
    private long openMilestoneCount;
    private long closeMilestoneCount;
    private List<ResponseMilestoneDto> milestones;

    private ResponseReadAllMilestonesDto(int allMilestoneCount, long openMilestoneCount, long closeMilestoneCount, List<ResponseMilestoneDto> responseMilestoneDtos) {
        this.allMilestoneCount = allMilestoneCount;
        this.openMilestoneCount = openMilestoneCount;
        this.closeMilestoneCount = closeMilestoneCount;
        this.milestones = responseMilestoneDtos;
    }

    public static ResponseReadAllMilestonesDto of(int allMilestoneCount, long openMilestonesCount, long closeMilestonesCount, List<ResponseMilestoneDto> milestones) {
        return new ResponseReadAllMilestonesDto(allMilestoneCount, openMilestonesCount, closeMilestonesCount, milestones);

    }
}
