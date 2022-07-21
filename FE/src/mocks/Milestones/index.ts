import { rest } from 'msw';

import { API } from '@/constants/api';
import { mockMilestones } from '@/mocks/Milestones/data';

const milestonesHandler = [
  rest.get(`/${API.PREFIX}/${API.MILESTONES}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockMilestones));
  }),
];

export default milestonesHandler;
