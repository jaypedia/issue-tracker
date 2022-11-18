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

export const changeFilterToInputQuery = (filter: IssueFilter, separator = ' ') => {
  let query = '';
  for (const [key, value] of Object.entries(filter)) {
    if (!value || !value.length || key === 'page') continue;
    if (key === QUERY_KEY.title) {
      query += `${value}${separator}`;
    } else if (key === QUERY_KEY.label) {
      value.forEach((v: string) => {
        query += `${key}:${v}${separator}`;
      });
    } else {
      query += `${key}:${value}${separator}`;
    }
  }

  return query.slice(0, -separator.length);
};

export const changeFilterToQueryString = (filter: IssueFilter) => {
  const separator = '|^&';
  const qs = changeFilterToInputQuery(filter, separator);
  const filterQuery = `q=${encodeURIComponent(qs)}`;
  const pageQuery = `page=${filter.page}`;

  if (filter.page && qs) {
    return `?${pageQuery}&${filterQuery}`;
  }
  if (filter.page) {
    return `?${pageQuery}`;
  }
  return `?${filterQuery}`;
};
