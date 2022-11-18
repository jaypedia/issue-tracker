import { API } from '@/constants/api';

const assingeeList = {
  indicator: 'Assignees',
  title: 'Assign up to 10 people to this issue',
  api: API.ASSIGNEES,
};

const labelList = {
  indicator: 'Labels',
  title: 'Apply labels to this issue',
  api: API.LABELS,
};

const milestoneList = {
  indicator: 'Milestone',
  title: 'Set milestone',
  api: API.MILESTONES,
};

export const getSideBarData = (assignees, labels, milestone) => {
  return [
    { defaultContents: 'No one', list: assingeeList, contents: assignees },
    { defaultContents: 'None yet', list: labelList, contents: labels },
    { defaultContents: 'No Milestone', list: milestoneList, contents: milestone },
  ];
};

export const initialSideBarData = [
  { defaultContents: 'No one', list: assingeeList },
  { defaultContents: 'None yet', list: labelList },
  { defaultContents: 'No Milestone', list: milestoneList },
];
