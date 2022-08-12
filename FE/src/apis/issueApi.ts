import axios from 'axios';

import { IssueType } from '@/types/issueTypes';

export const getIssueDetail = async <T>(id: number): Promise<T | undefined> => {
  try {
    const response = await axios.get(`/api/issues/${id}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return undefined;
  }
};

export const postIssue = async (issueData: IssueType) => {
  try {
    axios.post(`/api/issues`, issueData);
  } catch (error) {
    console.log(error);
  }
};

export const patchIssue = async (id: number, issueData: IssueType) => {
  try {
    axios.post(`/api/issues/${id}`, issueData);
  } catch (error) {
    console.log(error);
  }
};
