import { rest } from 'msw';

import { filterIssues, filterOpenIssues, filterClosedIssues } from './utils';

import { API } from '@/constants/api';
import { USER } from '@/constants/constants';
import { mockIssues } from '@/mocks/Issues/data';

const getLastIssueId = () => {
  const lastIndex = mockIssues.issues.length - 1;
  const lastIssue = mockIssues.issues[lastIndex];
  return lastIssue.id;
};

const getIssues: Parameters<typeof rest.get>[1] = (req, res, ctx) => {
  const params = req.url.search;
  const filteredIssues = filterIssues(params, mockIssues.issues);
  return res(ctx.status(200), ctx.json(filteredIssues));
};

const getOpenIssues: Parameters<typeof rest.get>[1] = (req, res, ctx) => {
  const openIssues = filterOpenIssues(mockIssues.issues);
  const data = { ...mockIssues, issues: openIssues };
  return res(ctx.status(200), ctx.json(data));
};

const getClosedIssues: Parameters<typeof rest.get>[1] = (req, res, ctx) => {
  const closedIssues = filterClosedIssues(mockIssues.issues);
  const data = { ...mockIssues, issues: closedIssues };
  return res(ctx.status(200), ctx.json(data));
};

const getIssueDetail: Parameters<typeof rest.get>[1] = (req, res, ctx) => {
  const { id } = req.params;
  const currentIssue = mockIssues.issues.find(issue => Number(id) === issue.id);
  return res(ctx.status(200), ctx.json(currentIssue));
};

const postIssue = (req, res, ctx) => {
  mockIssues.issues.push({
    author: USER.name,
    image: USER.image,
    id: getLastIssueId() + 1,
    createdAt: new Date().toString(),
    comments: [],
    commentCount: 0,
    issueStatus: 'open',
    title: req.body.title,
    content: req.body.content,
    ...req.body,
  });
  mockIssues.openIssueCount += 1;
  return res(ctx.status(201));
};

const patchIssue = (req, res, ctx) => {
  const { id } = req.params;
  const currentIssue = mockIssues.issues.find(issue => issue.id === Number(id));
  const filteredIssues = mockIssues.issues.filter(issue => issue.id !== Number(id));
  filteredIssues.push({ ...currentIssue, ...req.body });
  mockIssues.issues = filteredIssues;
  return res(ctx.status(204));
};

const postComment = (req, res, ctx) => {
  const { id: issueId } = req.params;
  const currentIssue = mockIssues.issues.find(issue => issue.id === Number(issueId));
  if (currentIssue) {
    currentIssue.comments.push({ id: Math.random(), ...req.body });
    currentIssue.commentCount += 1;
    return res(ctx.status(204));
  }
  return res(ctx.status(404));
};

const postAssignees = (req, res, ctx) => {
  const { id: issueId } = req.params;
  const assignee = req.body;
  const currentIssue = mockIssues.issues.find(issue => issue.id === Number(issueId));
  if (!currentIssue) return res(ctx.status(404));
  if (currentIssue.assignees.find(v => v.id === assignee.id)) {
    const filtered = currentIssue.assignees.filter(v => v.id !== assignee.id);
    currentIssue.assignees = filtered;
  } else {
    currentIssue.assignees.push(assignee);
  }
  return res(ctx.status(204));
};

const postLabels = (req, res, ctx) => {
  const { id: issueId } = req.params;
  const label = req.body;
  const currentIssue = mockIssues.issues.find(issue => issue.id === Number(issueId));
  if (!currentIssue) return res(ctx.status(404));
  if (currentIssue.labels.find(v => v.id === label.id)) {
    const filtered = currentIssue.labels.filter(v => v.id !== label.id);
    currentIssue.labels = filtered;
  } else {
    currentIssue.labels.push(label);
  }
  return res(ctx.status(204));
};

const postMilestone = (req, res, ctx) => {
  const { id: issueId } = req.params;
  const milestone = req.body;
  const currentIssue = mockIssues.issues.find(issue => issue.id === Number(issueId));
  if (!currentIssue) return res(ctx.status(404));
  currentIssue.milestones = [milestone];
  return res(ctx.status(204));
};

const deleteIssue = (req, res, ctx) => {
  const { id: issueId } = req.params;
  const filteredIssues = mockIssues.issues.filter(issue => issue.id !== Number(issueId));
  mockIssues.issues = filteredIssues;
  return res(ctx.status(204));
};

const markIssue = (req, res, ctx) => {
  const { ids, action } = req.body;
  ids.forEach(id => {
    const curIssue = mockIssues.issues.find(issue => issue.id === Number(id));
    if (curIssue) {
      curIssue.issueStatus = action;
    }
  });
  if (action === 'open') {
    mockIssues.openIssueCount += ids.length;
    mockIssues.closedIssueCount -= ids.length;
  } else {
    mockIssues.openIssueCount -= ids.length;
    mockIssues.closedIssueCount += ids.length;
  }
  return res(ctx.status(204));
};

const issueHandler = [
  rest.get(`/${API.PREFIX}/${API.ISSUES}`, getIssues),
  rest.get(`/${API.PREFIX}/${API.ISSUES}/open`, getOpenIssues),
  rest.get(`/${API.PREFIX}/${API.ISSUES}/closed`, getClosedIssues),
  rest.get(`/${API.PREFIX}/${API.ISSUES}/:id`, getIssueDetail),
  rest.post(`/${API.PREFIX}/${API.ISSUES}`, postIssue),
  rest.post(`/${API.PREFIX}/${API.ISSUES}/action`, markIssue),
  rest.post(`/${API.PREFIX}/${API.ISSUES}/:id`, patchIssue),
  rest.post(`/${API.PREFIX}/${API.ISSUES}/:id/comment`, postComment),
  rest.post(`/${API.PREFIX}/${API.ISSUES}/:id/assignees`, postAssignees),
  rest.post(`/${API.PREFIX}/${API.ISSUES}/:id/labels`, postLabels),
  rest.post(`/${API.PREFIX}/${API.ISSUES}/:id/milestone`, postMilestone),
  rest.delete(`/${API.PREFIX}/${API.ISSUES}/:id`, deleteIssue),
];

export default issueHandler;
