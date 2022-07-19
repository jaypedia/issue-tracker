package team20.issuetracker.domain.milestone.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ReadAllMilestones {

    private int allMilestoneCount;
    private long openMilestoneCount;
    private long closeMilestoneCount;
    private List<MilestoneDto> milestones;

    private ReadAllMilestones(int allMilestoneCount, long openMilestoneCount, long closeMilestoneCount, List<MilestoneDto> milestoneDtos) {
        this.allMilestoneCount = allMilestoneCount;
        this.openMilestoneCount = openMilestoneCount;
        this.closeMilestoneCount = closeMilestoneCount;
        this.milestones = milestoneDtos;
    }

    public static ReadAllMilestones of(int allMilestoneCount, long openMilestonesCount, long closeMilestonesCount, List<MilestoneDto> milestones) {
        return new ReadAllMilestones(allMilestoneCount, openMilestonesCount, closeMilestonesCount, milestones);

    }
}
