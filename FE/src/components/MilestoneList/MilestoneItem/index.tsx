import MilestoneDate from './MilestoneDate';
import * as S from './style';

import { editMilestone } from '@/apis/milestoneApi';
import Button from '@/components/common/Button';
import ProgressBar from '@/components/common/ProgressBar';
import { MILESTONE_STATUS } from '@/constants/constants';
import { useDeleteMilestone, useRefetchMilestone } from '@/hooks/useMilestone';
import * as I from '@/icons/Milestone';
import { Item } from '@/styles/list';
import { IMilestone } from '@/types/milestoneTypes';
import { getRelativeTime } from '@/utils/issue';

type MilestoneItemProps = IMilestone & {
  onEdit: () => void;
};

const MilestoneItem = ({
  id,
  title,
  dueDate,
  updatedAt,
  description,
  openIssueCount,
  closedIssueCount,
  milestoneStatus,
  onEdit,
}: MilestoneItemProps) => {
  const { mutate: deleteMilestone } = useDeleteMilestone(id);
  const { mutate: refetchMilestone } = useRefetchMilestone();

  const handleMilestoneDelete = () => {
    deleteMilestone();
  };

  const changeMilestoneStatus = () => {
    const status =
      milestoneStatus === MILESTONE_STATUS.closed ? MILESTONE_STATUS.open : MILESTONE_STATUS.closed;

    const milestoneData = {
      milestoneStatus: status,
    };

    editMilestone(id, milestoneData);
    refetchMilestone();
  };

  return (
    <Item key={id} type="milestone">
      <S.MilestoneInfoBox>
        <S.MilestoneTitle>{title}</S.MilestoneTitle>
        <S.MilestoneMetaBox>
          <MilestoneDate dueDate={dueDate} />
          <S.MilestoneMetaItem>
            <I.Clock />
            <S.MetaSentence>Last updated {getRelativeTime(updatedAt)}</S.MetaSentence>
          </S.MilestoneMetaItem>
        </S.MilestoneMetaBox>
        <p>{description}</p>
      </S.MilestoneInfoBox>

      <S.ProgressBarBox>
        <ProgressBar
          size="large"
          openIssueCount={openIssueCount}
          closedIssueCount={closedIssueCount}
        />
        <S.ButtonBox>
          <Button isText textColor="primary" text="Edit" onClick={onEdit} />
          <Button
            isText
            textColor="primary"
            text={milestoneStatus === MILESTONE_STATUS.open ? 'Close' : 'Reopen'}
            onClick={changeMilestoneStatus}
          />
          <Button isText textColor="warning" text="Delete" onClick={handleMilestoneDelete} />
        </S.ButtonBox>
      </S.ProgressBarBox>
    </Item>
  );
};

export default MilestoneItem;
