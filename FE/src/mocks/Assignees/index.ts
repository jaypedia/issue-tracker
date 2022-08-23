import { rest } from 'msw';

import { API } from '@/constants/api';
import { mockAssignees } from '@/mocks/Assignees/data';

const issueHandler = [
  rest.get(`/${API.PREFIX}/${API.ASSIGNEES}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockAssignees));
  }),

  rest.get(`/${API.PREFIX}/${API.ASSIGNEES}/:id`, (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json(mockAssignees.filter(v => v.id === Number(req.params.id))),
    );
  }),
];

export default issueHandler;
