export const ISSUE_STATUS = {
  open: 'open',
  closed: 'closed',
} as const;

export type IssueStatusType = typeof ISSUE_STATUS[keyof typeof ISSUE_STATUS];

export const QUERY_KEY = {
  author: 'author',
  label: 'label',
  milestone: 'milestone',
  assignees: 'assignees',
};
