package team20.issuetracker.domain.milestone.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ReadMilestoneListDto {

    private int allMilestoneCount;
    private long openMilestoneCount;
    private long closeMilestoneCount;
    private List<ReadMilestoneDto> milestones;

    private ReadMilestoneListDto(int allMilestoneCount, long openMilestoneCount, long closeMilestoneCount, List<ReadMilestoneDto> readMilestoneDtos) {
        this.allMilestoneCount = allMilestoneCount;
        this.openMilestoneCount = openMilestoneCount;
        this.closeMilestoneCount = closeMilestoneCount;
        this.milestones = readMilestoneDtos;
    }

    public static ReadMilestoneListDto of(int allMilestoneCount, long openMilestonesCount, long closeMilestonesCount, List<ReadMilestoneDto> milestones) {
        return new ReadMilestoneListDto(allMilestoneCount, openMilestonesCount, closeMilestonesCount, milestones);

    }
}
