import { rest } from 'msw';

import { filterMilestones } from './utils';

import { API } from '@/constants/api';
import { mockMilestones } from '@/mocks/Milestones/data';

const getMilestones = (req, res, ctx) => {
  const filtered = filterMilestones(req.url.search, mockMilestones.milestones);
  const mock = { ...mockMilestones, milestones: filtered };
  return res(ctx.status(200), ctx.json(mock));
};

const deleteMilestone = (req, res, ctx) => {
  const { id } = req.params;
  const currentMilestone = mockMilestones.milestones.find(milestone => milestone.id === Number(id));
  const filteredMilestones = mockMilestones.milestones.filter(
    milestone => milestone.id !== Number(id),
  );
  mockMilestones.milestones = filteredMilestones;
  mockMilestones.allMileStonesCount -= 1;
  if (currentMilestone?.milestoneStatus === 'open') {
    mockMilestones.openMileStonesCount -= 1;
  } else {
    mockMilestones.closedMileStonesCount -= 1;
  }
  return res(ctx.status(204));
};

const postMilestone = (req, res, ctx) => {
  mockMilestones.milestones.push({
    id: Math.random(),
    ...req.body,
    openIssueCount: 0,
    closedIssueCount: 0,
    milestoneStatus: 'open',
  });
  mockMilestones.allMileStonesCount += 1;
  mockMilestones.openMileStonesCount += 1;
  return res(ctx.status(201));
};

const patchMilestone = (req, res, ctx) => {
  const { id } = req.params;
  const currentMilestone = mockMilestones.milestones.find(milestone => milestone.id === Number(id));
  const filteredMilestones = mockMilestones.milestones.filter(
    milestone => milestone.id !== Number(id),
  );
  filteredMilestones.push({ ...currentMilestone, ...req.body });
  mockMilestones.milestones = filteredMilestones;
  if (req.body.milestoneStatus === 'closed') {
    mockMilestones.closedMileStonesCount += 1;
    mockMilestones.openMileStonesCount -= 1;
  } else if (req.body.milestoneStatus === 'open') {
    mockMilestones.closedMileStonesCount -= 1;
    mockMilestones.openMileStonesCount += 1;
  }
  return res(ctx.status(204));
};

const milestonesHandler = [
  rest.get(`/${API.PREFIX}/${API.MILESTONES}`, getMilestones),
  rest.post(`/${API.PREFIX}/${API.MILESTONES}`, postMilestone),
  rest.delete(`/${API.PREFIX}/${API.MILESTONES}/:id`, deleteMilestone),
  rest.post(`/${API.PREFIX}/${API.MILESTONES}/:id`, patchMilestone),
];

export default milestonesHandler;
