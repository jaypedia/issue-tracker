import { atom } from 'recoil';

export const sideBarInitialState = {
  Assignees: [],
  Labels: [],
  Milestone: [],
};

export const sideBarState = atom({
  key: 'sideBarState',
  default: sideBarInitialState,
});
