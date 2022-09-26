/* eslint-disable react/jsx-props-no-spreading */
import MilestoneItem from './MilestoneItem';
import MilestoneListHeader from './MilestoneListHeader';

import { MilestoneType, MilestoneDataType } from '@/types/milestoneTypes';

type MilestoneListProps = {
  data: MilestoneDataType;
  onEdit: (props: MilestoneType) => void;
};

const MilestoneList = ({ data, onEdit }: MilestoneListProps) => {
  return (
    <>
      <MilestoneListHeader open={data.openMilestoneCount} closed={data.closedMilestoneCount} />
      {data.milestones.map((props: MilestoneType) => (
        <MilestoneItem key={props.id} {...props} onEdit={() => onEdit(props)} />
      ))}
    </>
  );
};

export default MilestoneList;
