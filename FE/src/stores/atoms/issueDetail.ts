import { atom } from 'recoil';

export const issueDetail = {
  title: '',
};

export const issueDetailState = atom<{ title: string }>({
  key: 'issueDetail',
  default: issueDetail,
});
