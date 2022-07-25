type UniformMenuType = 'Author' | 'Assignee' | 'Label' | 'Milestone' | 'Filter';

export const getUniformMenus = (type: UniformMenuType, list) => {
  switch (type) {
    case 'Author':
    case 'Assignee':
      return list.menus.map(v => ({ ...v, name: v.userId }));
    case 'Label':
    case 'Milestone':
      return list.menus.map(v => ({ ...v, name: v.title }));
    case 'Filter':
      return list.menus.map((v, i) => ({ id: i, name: v }));
    default:
      return list.menus;
    // throw Error('Invaild menu type');
  }
};
