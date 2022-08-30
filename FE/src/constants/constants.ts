export const ISSUE_STATUS = {
  open: 'open',
  closed: 'closed',
  createdByMe: 'created_by/me',
  assignedByMe: 'assigned_by/me',
  commentedByMe: 'commented_by/me',
} as const;

export const QUERY_KEY = {
  author: 'author',
  label: 'label',
  milestone: 'milestone',
  assignee: 'assignee',
  title: 'title',
};

export const INDICATOR = {
  setting: 'setting',
};

export const FORM_TYPE = {
  create: 'create',
  edit: 'edit',
};

export const MILESTONE_STATUS = {
  open: 'open',
  closed: 'closed',
} as const;

export const COMMENT_FORM_TYPE = {
  edit: 'edit',
  comment: 'comment',
};

export const USER = {
  name: 'Guest',
  image:
    'https://www.popphoto.com/uploads/2022/02/07/Craig-Sellars.jpeg?auto=webp&width=1440&height=961.171875',
};

export const FILTER = {
  author: 'author',
  label: 'label',
  milestone: 'milestone',
  assignee: 'assignee',
} as const;
