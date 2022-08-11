export type MilestoneStatus = 'open' | 'closed';

export interface MilestoneType {
  id: number;
  title: string;
  dueDate: string;
  updatedAt: string;
  description: string;
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
