import issueListHandler from '@/mocks/issueList';
import LabelsHandler from '@/mocks/Labels';
import MilestonesHandler from '@/mocks/Milestones';

const handlers = [...issueListHandler, ...LabelsHandler, ...MilestonesHandler];

export default handlers;
