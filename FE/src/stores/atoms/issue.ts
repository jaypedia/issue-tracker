import { atom } from 'recoil';

import { ISSUE_STATUS } from '@/constants/constants';
import { IssueFilter } from '@/types/issueTypes';

export const initialIssueStatus = {
  is: ISSUE_STATUS.open,
  title: undefined,
  author: undefined,
  label: [],
  milestone: undefined,
  assignee: undefined,
  commentedBy: undefined,
  page: 0,
};

export const issueStatusState = atom<IssueFilter>({
  key: 'issueStatus',
  default: initialIssueStatus,
});
