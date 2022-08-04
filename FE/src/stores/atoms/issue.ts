import { atom } from 'recoil';

import { ISSUE_STATUS } from '@/constants/constants';

type IssueStatus = 'open' | 'closed';

export const issueStatusState = atom<IssueStatus>({
  key: 'issueStatus',
  default: ISSUE_STATUS.open,
});
