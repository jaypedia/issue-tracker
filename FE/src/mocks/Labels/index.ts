import { rest } from 'msw';

import { API } from '@/constants/api';
import { mockLabels } from '@/mocks/Labels/data';

const issueHandler = [
  rest.get(`/${API.PREFIX}/${API.LABELS}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockLabels));
  }),
];

export default issueHandler;
