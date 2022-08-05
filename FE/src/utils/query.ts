import axios from 'axios';
import { useQuery } from 'react-query';

import { IssueStatusType, IssueDataType } from '@/types/issueTypes';

export const fetchIssue = async <T>(issueStatus: IssueStatusType): Promise<T> => {
  const response = await axios.get(`/api/issues?issueStatus=${issueStatus}`);
  return response.data;
};

export const useIssueQuery = (issueStatus: IssueStatusType) => {
  return useQuery(['issueList', issueStatus], () => fetchIssue<IssueDataType>(issueStatus));
};
