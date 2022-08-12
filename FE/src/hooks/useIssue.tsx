import { useMutation, useQueryClient, useQuery } from 'react-query';

import { fetchAPI } from '@/apis/common';
import { getIssueDetail } from '@/apis/issueApi';
import { API } from '@/constants/api';
import { IssueStatusType, IssuesDataType, IssueType } from '@/types/issueTypes';

export const useGetIssue = (issueStatus: IssueStatusType) => {
  return useQuery([API.ISSUES, issueStatus], () =>
    fetchAPI<IssuesDataType>(API.ISSUES, { params: issueStatus }),
  );
};

export const useGetIssueDetail = (id: number) => {
  return useQuery(['issueDetail', id], () => getIssueDetail<IssueType>(id));
};

export const useRefetchIssue = () => {
  const queryClient = useQueryClient();
  return useMutation(() => fetchAPI('issues'), {
    onSuccess: () => {
      queryClient.invalidateQueries('issues');
    },
  });
};

export const useRefetchIssueDetail = (id: number) => {
  const queryClient = useQueryClient();
  return useMutation(() => getIssueDetail(id), {
    onSuccess: () => {
      queryClient.invalidateQueries('issueDetail');
    },
  });
};
