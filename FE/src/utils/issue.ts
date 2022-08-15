import moment from 'moment';

import { QUERY_KEY, ISSUE_STATUS } from '@/constants/constants';
/* eslint-disable no-loop-func */
import { IssueFilter } from '@/types/issueTypes';
/* eslint-disable no-continue */
/* eslint-disable no-restricted-syntax */

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

export const changeFilterToQueryString = (filter: IssueFilter) => {
  let qs = '?';
  for (const [key, value] of Object.entries(filter)) {
    if (!value || !value.length) continue;
    if (key === QUERY_KEY.label) {
      value.forEach((v: string) => {
        qs += `${key}=${v}&`;
      });
    } else {
      qs += `${key}=${value}&`;
    }
  }

  return qs.slice(0, -1);
};

export const changeFilterToInputQuery = (filter: IssueFilter) => {
  let query = '';
  for (const [key, value] of Object.entries(filter)) {
    if (!value || !value.length) continue;
    if (key === QUERY_KEY.title) {
      query += `${value} `;
    } else if (key === QUERY_KEY.label) {
      value.forEach((v: string) => {
        query += `${key}:${v} `;
      });
    } else {
      query += `${key}:${value} `;
    }
  }

  return query;
};
