import moment from 'moment';

import { ISSUE_STATUS } from '@/constants/constants';

export const getRelativeTime = (createdAt: string) => {
  return moment(createdAt).fromNow();
};

type getIssueDetailInfoSentenceParams = {
  author: string;
  createdAt: string;
  commentCount: number;
};

type getIssueInfoSentenceParams = {
  issueId: number;
  issueStatus: string;
  author: string;
  createdAt: string;
};

export const getIssueDetailInfoSentence = ({
  author,
  createdAt,
  commentCount,
}: getIssueDetailInfoSentenceParams) => {
  return `${author} opened this issue ${getRelativeTime(createdAt)} · ${commentCount} comments`;
};

export const getIssueInfoSentence = ({
  issueId,
  issueStatus,
  author,
  createdAt,
}: getIssueInfoSentenceParams) => {
  switch (issueStatus) {
    case ISSUE_STATUS.open:
      return `#${issueId} opened ${getRelativeTime(createdAt)} by ${author}`;
    case ISSUE_STATUS.closed:
      return `#${issueId} by ${author} was closed ${getRelativeTime(createdAt)} `;
    default:
      throw new Error('Status is not correct');
  }
};

export const convertFirstLetterToUppercase = (word: string | undefined): string => {
  if (!word) return '';
  return word.replace(/^[a-z]/, char => char.toUpperCase());
};
