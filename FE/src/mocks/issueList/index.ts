import { rest } from 'msw';

import { API } from '@/constants/api';
import { mockIssueList, mockIssueDetail } from '@/mocks/issueList/data';

const issueHandler = [
  rest.get(`/${API.PREFIX}/${API.ISSUES}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockIssueList));
  }),

  rest.get(`/${API.PREFIX}/${API.ISSUES}/:id`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockIssueDetail));
  }),
];

export default issueHandler;
