export type MilestoneStatus = 'open' | 'closed';
export interface MilestoneApiData {
  title: string;
  dueDate: string;
  updatedAt: string;
  description: string;
}
export interface MilestoneStatusData {
  milestoneStatus: MilestoneStatus;
}
export interface MilestoneType extends MilestoneApiData {
  id: number;
  openIssueCount: number;
  closedIssueCount: number;
  milestoneStatus: string;
}

export interface MilestoneDataType {
  allMileStonesCount: number;
  openMileStonesCount: number;
  closedMileStonesCount: number;
  milestones: MilestoneType[];
}
