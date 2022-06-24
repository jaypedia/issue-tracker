import moment from 'moment';

import { ISSUE_STATUS } from '@/constants/constants';

const getRelativeTime = (issueCreateTime: string) => {
  return moment(issueCreateTime).fromNow();
};

type getIssueInfoSentenceParams = {
  issueId: number;
  author: string;
  issueCreateTime: string;
  issueStatus: string;
  commentCount?: number;
};

export const getIssueInfoSentence = ({
  issueId,
  issueStatus,
  author,
  issueCreateTime,
  commentCount,
}: getIssueInfoSentenceParams) => {
  if (commentCount && commentCount >= 0) {
    return `${author} opened this issue ${getRelativeTime(
      issueCreateTime,
    )} · ${commentCount} comments`;
  }

  switch (issueStatus) {
    case ISSUE_STATUS.open:
      return `#${issueId} opened ${getRelativeTime(issueCreateTime)} by ${author}`;
    case ISSUE_STATUS.closed:
      return `#${issueId} by ${author} was closed ${getRelativeTime(issueCreateTime)} `;
    default:
      throw new Error('Status is not correct');
  }
};

export const convertFirstLetterToUppercase = (word: string) => {
  return word.replace(/^[a-z]/, char => char.toUpperCase());
};
