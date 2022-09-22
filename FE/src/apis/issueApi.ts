import axios from 'axios';

import { IssueEditDataType, CommentDataType, PostIssueType } from '@/types/issueTypes';

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

// TODO: sideBarData Type - assignees, labels, milestone
export const editSideBar = async (id: number, indicator: string, sideBarData) => {
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
