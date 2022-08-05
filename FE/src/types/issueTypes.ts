import { IssueItem } from '@/components/IssueList/type';

export type IssueStatusType = 'open' | 'closed';

export interface IssueDataType {
  issues: IssueItem[];
  OpenIssueCount: number;
  ClosedIssueCount: number;
}
