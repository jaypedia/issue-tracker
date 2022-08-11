/* eslint-disable react/jsx-props-no-spreading */
import MilestoneItem from './MilestoneItem';
import MilestoneListHeader from './MilestoneListHeader';

import { MilestoneType, MilestoneDataType } from '@/types/milestoneTypes';

type MilestoneListProps = {
  data: MilestoneDataType;
  onEdit: () => void;
};

const MilestoneList = ({ data, onEdit }: MilestoneListProps) => {
  return (
    <>
      <MilestoneListHeader open={data.openMileStonesCount} closed={data.closedMileStonesCount} />
      {data.milestones.map((props: MilestoneType) => (
        <MilestoneItem key={props.id} {...props} onEdit={onEdit} />
      ))}
    </>
  );
};

export default MilestoneList;
