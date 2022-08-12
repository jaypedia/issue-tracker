import { useQuery } from 'react-query';

import { fetchAPI } from '@/apis/common';
import { getIssueDetail } from '@/apis/issueApi';
import { API } from '@/constants/api';
import { IssueStatusType, IssuesDataType, IssueType } from '@/types/issueTypes';

export const useGetIssue = (issueStatus: IssueStatusType) => {
  return useQuery(['issueList', issueStatus], () =>
    fetchAPI<IssuesDataType>(API.ISSUES, { params: issueStatus }),
  );
};

export const useGetIssueDetail = (id: number) => {
  return useQuery('issueDetail', () => getIssueDetail<IssueType>(id));
};
