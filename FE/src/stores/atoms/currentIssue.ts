import { atom } from 'recoil';

import { IssueEditDataType } from '../../types/issueTypes';

export const currentIssue = {
  title: '',
  createdAt: '',
  author: '',
  image: '',
  content: '',
};

export const currentIssueState = atom<IssueEditDataType>({
  key: 'currentIssue',
  default: currentIssue,
});
