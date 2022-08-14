import { rest } from 'msw';

import { API } from '@/constants/api';
import { mockLabels } from '@/mocks/Labels/data';

const getLabels = (req, res, ctx) => {
  return res(ctx.status(200), ctx.json(mockLabels));
};

const deleteLabel = (req, res, ctx) => {
  const { id } = req.params;
  const filteredLabels = mockLabels.labels.filter(label => label.id !== Number(id));
  mockLabels.labelCount -= 1;
  mockLabels.labels = filteredLabels;
  return res(ctx.status(204));
};

const postLabel = (req, res, ctx) => {
  mockLabels.labels.push({ id: Math.random(), ...req.body });
  mockLabels.labelCount += 1;
  return res(ctx.status(201));
};

const patchLabel = (req, res, ctx) => {
  const { id } = req.params;
  const filteredLabels = mockLabels.labels.filter(label => label.id !== Number(id));
  filteredLabels.push({ id: Number(id), ...req.body });
  mockLabels.labels = filteredLabels;
  return res(ctx.status(204));
};

const labelsHandler = [
  rest.get(`/${API.PREFIX}/${API.LABELS}`, getLabels),
  rest.delete(`/${API.PREFIX}/${API.LABELS}/:id`, deleteLabel),
  rest.post(`/${API.PREFIX}/${API.LABELS}`, postLabel),
  rest.patch(`/${API.PREFIX}/${API.LABELS}/:id`, patchLabel),
];

export default labelsHandler;
