/* eslint-disable react/jsx-props-no-spreading */
import MilestoneItem from './MilestoneItem';

import { ListHeader } from '@/styles/list';
import { MilestoneType, MilestoneDataType } from '@/types/milestoneTypes';

type MilestoneListProps = {
  data: MilestoneDataType;
  onEdit: () => void;
};

const MilestoneList = ({ data, onEdit }: MilestoneListProps) => {
  return (
    <>
      <ListHeader type="large">{data.allMileStonesCount} Milestones</ListHeader>
      {data.milestones.map((props: MilestoneType) => (
        <MilestoneItem key={props.id} {...props} onEdit={onEdit} />
      ))}
    </>
  );
};

export default MilestoneList;
