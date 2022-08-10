import MilestoneDate from './MilestoneDate';
import * as S from './style';

import Button from '@/components/common/Button';
import ProgressBar from '@/components/common/ProgressBar';
import * as I from '@/icons/Milestone';
import { Item } from '@/styles/list';
import { MilestoneType } from '@/types/milestoneTypes';
import { getRelativeTime } from '@/utils/issue';

const MilestoneItem = ({
  id,
  title,
  dueDate,
  updatedAt,
  description,
  openIssueCount,
  closedIssueCount,
  onEdit,
}: MilestoneType) => {
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
          <Button isText textColor="primary" text="Close" />
          <Button isText textColor="warning" text="Delete" />
        </S.ButtonBox>
      </S.ProgressBarBox>
    </Item>
  );
};

export default MilestoneItem;
