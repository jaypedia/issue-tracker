import TabBar from './TabBar';

import { ListHeader } from '@/styles/list';

type MilestoneCount = {
  open: number;
  closed: number;
};

const MilestoneListHeader = ({ open, closed }: MilestoneCount) => {
  return (
    <ListHeader type="large">
      <TabBar openCount={open} closedCount={closed} />
    </ListHeader>
  );
};

export default MilestoneListHeader;
