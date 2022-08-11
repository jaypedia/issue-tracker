import { atom } from 'recoil';

import { MILESTONE_STATUS } from '@/constants/constants';

type MilestoneStatus = 'open' | 'closed';

export const milestoneStatusState = atom<MilestoneStatus>({
  key: 'milestoneStatus',
  default: MILESTONE_STATUS.open,
});
