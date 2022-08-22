type UniformMenuType = 'author' | 'assignee' | 'label' | 'milestone';

export const getUniformMenus = (type: UniformMenuType, list: any) => {
  switch (type) {
    case 'author':
    case 'assignee':
      return list.map(v => ({ ...v, name: v.userId }));
    case 'label':
      return list.labels.map(v => ({ ...v, name: v.title }));
    case 'milestone':
      return list.milestones.map(v => ({ ...v, name: v.title }));
    default:
      throw Error('Invaild menu type');
  }
};
