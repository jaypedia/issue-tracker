import { rest } from 'msw';

import { filterIssues } from './utils';

import { API } from '@/constants/api';
import { mockIssues } from '@/mocks/Issues/data';

const getLastIssueId = () => {
  const lastIndex = mockIssues.issues.length - 1;
  const lastIssue = mockIssues.issues[lastIndex];
  return lastIssue.id;
};

const getIssues: Parameters<typeof rest.get>[1] = (req, res, ctx) => {
  const filteredIssues = filterIssues(req.url.search, mockIssues.issues);
  return res(ctx.status(200), ctx.json(filteredIssues));
};

const getIssueDetail = (req, res, ctx) => {
  const { id } = req.params;
  const currentIssue = mockIssues.issues.find(issue => Number(id) === issue.id);
  return res(ctx.status(200), ctx.json(currentIssue));
};

const postIssue = (req, res, ctx) => {
  mockIssues.issues.push({ id: getLastIssueId() + 1, ...req.body });
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
  rest.get(`/${API.PREFIX}/${API.ISSUES}/:id`, getIssueDetail),
  rest.post(`/${API.PREFIX}/${API.ISSUES}`, postIssue),
  rest.post(`/${API.PREFIX}/${API.ISSUES}/:id`, patchIssue),
  rest.post(`/${API.PREFIX}/${API.ISSUES}/:id/comment`, postComment),
];

export default issueHandler;
