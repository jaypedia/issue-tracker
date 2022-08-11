import moment from 'moment';

import * as S from '../style';

import * as I from '@/icons/Milestone';

const MilestoneDate = ({ dueDate }: { dueDate: string }) => {
  const today = new Date();
  const isPast = new Date(dueDate) < today;

  if (!dueDate) {
    return (
      <S.MilestoneMetaItem>
        <S.MetaSentence>No due date</S.MetaSentence>
      </S.MilestoneMetaItem>
    );
  }

  if (isPast) {
    return (
      <S.MilestoneMetaItem>
        <I.Warning />
        <S.MetaSentence type="warning">
          Past due by {moment(today).diff(dueDate, 'days')} days
        </S.MetaSentence>
      </S.MilestoneMetaItem>
    );
  }

  return (
    <S.MilestoneMetaItem>
      <I.Calendar />
      <S.MetaSentence>Due by {moment(dueDate).format('MMMM D, YYYY')}</S.MetaSentence>
    </S.MilestoneMetaItem>
  );
};

export default MilestoneDate;
