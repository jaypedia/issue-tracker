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
export interface IMilestone extends MilestoneApiData {
  id: number;
  openIssueCount: number;
  closedIssueCount: number;
  milestoneStatus: string;
}

export interface MilestoneDataType {
  allMilestoneCount: number;
  openMilestoneCount: number;
  closedMilestoneCount: number;
  milestones: IMilestone[];
}
