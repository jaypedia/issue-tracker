import axios from 'axios';

import { IssueType, IssueEditDataType, CommentDataType } from '@/types/issueTypes';

export const getFilteredIssues = async <T>(filter: string): Promise<T | undefined> => {
  try {
    const response = await axios.get(`/api/issues/${filter}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return undefined;
  }
};

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

export const patchIssue = async (id: number, issueData: IssueEditDataType | CommentDataType) => {
  try {
    axios.post(`/api/issues/${id}`, issueData);
  } catch (error) {
    console.log(error);
  }
};

export const postComment = async (id: number, commentData: CommentDataType) => {
  try {
    axios.post(`/api/issues/${id}/comment`, commentData);
  } catch (error) {
    console.log(error);
  }
};
