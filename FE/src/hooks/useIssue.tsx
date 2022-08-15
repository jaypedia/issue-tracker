import { useMutation, useQueryClient, useQuery } from 'react-query';

import { fetchAPI } from '@/apis/common';
import { getFilteredIssues, getIssueDetail } from '@/apis/issueApi';
import { API } from '@/constants/api';
import { IssuesDataType, IssueType } from '@/types/issueTypes';
import { changeFilterToQueryString } from '@/utils/issue';

export const useGetIssue = filter => {
  console.log(filter);
  console.log(changeFilterToQueryString(filter));
  // TODO: 임시 filter.is 수정
  return useQuery([API.ISSUES, filter], () => getFilteredIssues<IssuesDataType>(filter.is));
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

export const useSearchIssue = (searchWord: string) => {
  return useQuery([API.ISSUES, searchWord], () =>
    fetchAPI<IssuesDataType>(API.ISSUES, { title: searchWord }),
  );
};
