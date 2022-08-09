import axios from 'axios';
import { useQuery } from 'react-query';

import { API } from '@/constants/api';
import { IssueStatusType, IssueDataType } from '@/types/issueTypes';
import { LabelDataType } from '@/types/labelTypes';

type API = 'issues' | 'labels' | 'milestones';

export const fetchAPI = async <T>(apiName: API, params?: object): Promise<T | undefined> => {
  try {
    const response = await axios.get(`/api/${apiName}`, { params });
    return response.data;
  } catch (error) {
    console.log(error);
    return undefined;
  }
};

export const useIssueQuery = (issueStatus: IssueStatusType) => {
  return useQuery(['issueList', issueStatus], () =>
    fetchAPI<IssueDataType>(API.ISSUES, { params: issueStatus }),
  );
};

export const useLabelQuery = () => {
  return useQuery([API.LABELS], () => fetchAPI<LabelDataType>(API.LABELS));
};

export const useMilestoneQuery = () => {
  return useQuery([API.MILESTONES], () => fetchAPI<LabelDataType>(API.MILESTONES));
};
