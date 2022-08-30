import AssigneesHandler from '@/mocks/Assignees';
import IssueHandler from '@/mocks/Issues';
import LabelsHandler from '@/mocks/Labels';
import LoginHandler from '@/mocks/Login';
import MilestonesHandler from '@/mocks/Milestones';

const handlers = [
  ...IssueHandler,
  ...LabelsHandler,
  ...MilestonesHandler,
  ...AssigneesHandler,
  ...LoginHandler,
];

export default handlers;
