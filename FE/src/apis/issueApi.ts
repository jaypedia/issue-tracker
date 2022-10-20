import axios from 'axios';

import {
  ModifiedAssignee,
  IssueEditDataType,
  CommentDataType,
  PostIssueType,
} from '@/types/issueTypes';

export const getFilteredIssues = async <T>(filter: string): Promise<T | undefined> => {
  try {
    const response = await axios.get(`/api/issues${filter}`);
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

export const postIssue = async (issueData: PostIssueType) => {
  try {
    axios.post(`/api/issues`, issueData);
  } catch (error) {
    console.log(error);
  }
};

export const editIssue = async (id: number, issueData: IssueEditDataType) => {
  try {
    axios.post(`/api/issues/${id}`, issueData);
  } catch (error) {
    console.log(error);
  }
};

export const postComment = async (commentData: CommentDataType, id?: number) => {
  try {
    axios.post(`/api/comments/${id}`, commentData);
  } catch (error) {
    console.log(error);
  }
};

export const deleteComment = async (commentId: number) => {
  try {
    axios.delete(`/api/comments/${commentId}`);
  } catch (error) {
    console.log(error);
  }
};

// TODO: sideBarData Type - assignees, labels, milestone
export const editSideBar = async (id: number, indicator: string, sideBarData: ModifiedAssignee) => {
  try {
    axios.post(`/api/issues/${id}/${indicator}`, sideBarData);
  } catch (error) {
    console.log(error);
  }
};

export const deleteIssue = async (id: number) => {
  try {
    axios.delete(`/api/issues/${id}`);
  } catch (error) {
    console.log(error);
  }
};

type ActionIssueType = {
  ids: number[];
  action: string;
};

export const actionIssues = async (issues: ActionIssueType) => {
  try {
    axios.post(`/api/issues/action`, issues);
  } catch (error) {
    console.log(error);
  }
};
