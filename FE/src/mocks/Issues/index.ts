import { rest } from 'msw';

import { filterIssues, filterOpenIssues, filterClosedIssues } from './utils';

import { API } from '@/constants/api';
import { USER } from '@/constants/constants';
import { mockAssignees } from '@/mocks/Assignees/data';
import { mockIssues } from '@/mocks/Issues/data';
import { mockLabels } from '@/mocks/Labels/data';
import { mockMilestones } from '@/mocks/Milestones/data';

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

const getOpenIssues = (req, res, ctx) => {
  const openIssues = filterOpenIssues(mockIssues.issues);
  const data = { ...mockIssues, issues: openIssues };
  return res(ctx.status(200), ctx.json(data));
};

const getClosedIssues = (req, res, ctx) => {
  const closedIssues = filterClosedIssues(mockIssues.issues);
  const data = { ...mockIssues, issues: closedIssues };
  return res(ctx.status(200), ctx.json(data));
};

const getIssueDetail = (req, res, ctx) => {
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
    // milestones: mockMilestones.milestones.filter(v => v.id === req.body.milestoneId),
    // labels: req.body.labelIds.map(v => mockLabels.labels.filter(l => l.id === v)),
    // assignees: req.body.assigneeIds.map(v => mockAssignees.filter(l => l.id === v)),
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

const issueHandler = [
  rest.get(`/${API.PREFIX}/${API.ISSUES}`, getIssues),
  rest.get(`/${API.PREFIX}/${API.ISSUES}/open`, getOpenIssues),
  rest.get(`/${API.PREFIX}/${API.ISSUES}/closed`, getClosedIssues),
  rest.get(`/${API.PREFIX}/${API.ISSUES}/:id`, getIssueDetail),
  rest.post(`/${API.PREFIX}/${API.ISSUES}`, postIssue),
  rest.post(`/${API.PREFIX}/${API.ISSUES}/:id`, patchIssue),
  rest.post(`/${API.PREFIX}/${API.ISSUES}/:id/comment`, postComment),
];

export default issueHandler;
