import { atom } from 'recoil';

import { Assignee } from '@/types/issueTypes';
import { LabelData } from '@/types/labelTypes';
import { MilestoneType } from '@/types/milestoneTypes';

type sideBarType = {
  Assignees: Assignee[] | [];
  Labels: LabelData[] | [];
  Milestone: MilestoneType[] | [];
};

export const sideBarInitialState: sideBarType = {
  Assignees: [],
  Labels: [],
  Milestone: [],
};

export const sideBarState = atom({
  key: 'sideBarState',
  default: sideBarInitialState,
});
