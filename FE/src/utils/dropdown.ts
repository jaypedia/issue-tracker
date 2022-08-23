type UniformMenuType =
  | 'author'
  | 'assignee'
  | 'label'
  | 'milestone'
  | 'Assignees'
  | 'Labels'
  | 'Milestone';

export const getUniformMenus = (type: UniformMenuType, list: any) => {
  switch (type) {
    case 'author':
    case 'assignee':
    case 'Assignees':
      return list.map(v => ({ ...v, name: v.userId }));
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
