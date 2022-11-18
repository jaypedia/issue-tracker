export type MilestoneStatus = 'open' | 'closed';
export interface MilestoneApiData {
  title: string;
  dueDate: string;
  updatedAt: string;
  description: string;
}
export interface MilestoneEditData extends MilestoneApiData {
  milestoneStatus: MilestoneStatus;
}
export interface IMilestone extends MilestoneEditData {
  id: number;
  openIssueCount: number;
  closedIssueCount: number;
}

export interface MilestoneDataType {
  allMilestoneCount: number;
  openMilestoneCount: number;
  closedMilestoneCount: number;
  milestones: IMilestone[];
}

export interface ModifiedMilestone extends IMilestone {
  name: string;
}
