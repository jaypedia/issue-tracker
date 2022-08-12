import { ILabel } from '@/types/labelTypes';
import { MilestoneType } from '@/types/milestoneTypes';

export type IssueStatusType = 'open' | 'closed';

export type Assignee = {
  id: number;
  userId: string;
  image: string;
};

type Comment = {
  id: number;
  author: string;
  image: string;
  content: string;
  createdAt: string;
};

export type IssueType = {
  id: number;
  issueTitle: string;
  author: string;
  createdAt: string;
  image: string;
  commentCount: number;
  content: string;
  milestone: MilestoneType;
  comments: Comment[];
  labels: ILabel[];
  assignees: Assignee[];
  issueStatus: IssueStatusType;
};
export interface IssuesDataType {
  openIssueCount: number;
  closedIssueCount: number;
  issues: IssueType[] | [];
}
