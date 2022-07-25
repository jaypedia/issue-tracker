import { rest } from 'msw';

import { filterIssues } from '../utils';

import { API } from '@/constants/api';
import { mockIssueList, mockIssueDetail } from '@/mocks/Issues/data';

const getIssues: Parameters<typeof rest.get>[1] = (req, res, ctx) => {
  // console.log(req.url.search);

  const filteredIssues = filterIssues(req.url.search, mockIssueList);
  console.log(filteredIssues);

  return res(ctx.status(200), ctx.json(filteredIssues));
};

const issueHandler = [
  rest.get(`/${API.PREFIX}/${API.ISSUES}`, getIssues),

  rest.get(`/${API.PREFIX}/${API.ISSUES}/:id`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockIssueDetail));
  }),
];

export default issueHandler;
