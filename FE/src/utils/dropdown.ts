import { AssigneeDataType } from '@/types/issueTypes';
import { LabelDataType } from '@/types/labelTypes';
import { MilestoneDataType } from '@/types/milestoneTypes';

export type UniformMenuType =
  | 'author'
  | 'assignee'
  | 'label'
  | 'milestone'
  | 'Assignees'
  | 'Labels'
  | 'Milestone';

export type ListDataType = AssigneeDataType | LabelDataType | MilestoneDataType;

export const getUniformMenus = (type: UniformMenuType, list: ListDataType) => {
  switch (type) {
    case 'author':
    case 'assignee':
    case 'Assignees':
      return list.assignees.map(v => ({ ...v, name: v.userId }));
    case 'label':
    case 'Labels':
      return list.labels.map(v => ({ ...v, name: v.title }));
    case 'milestone':
    case 'Milestone':
      return list.milestones.map(v => ({ ...v, name: v.title }));
    default:
      throw Error('Invaild menu type');
  }
};
