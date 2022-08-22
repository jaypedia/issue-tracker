import { API } from '@/constants/api';

const authorList = {
  indicator: 'Author',
  title: 'Filter by author',
  filter: 'author',
  api: API.ASSIGNEES,
};

const labelList = {
  indicator: 'Label',
  title: 'Filter by label',
  filter: 'label',
  api: API.LABELS,
};

const milestoneList = {
  indicator: 'Milestone',
  title: 'Filter by milestone',
  filter: 'milestone',
  api: API.MILESTONES,
};

const assingeeList = {
  indicator: 'Assignee',
  title: 'Filter by who’s assigned',
  filter: 'assignee',
  api: API.ASSIGNEES,
};

export const HeaderDropDownList = [authorList, labelList, milestoneList, assingeeList];
