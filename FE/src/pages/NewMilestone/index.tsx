import * as S from './style';

import MilestoneForm from '@/components/MilestoneList/MilestoneItem/MilestoneForm';
import { ColumnWrapper, Heading1 } from '@/styles/common';

const NewMilestone = () => {
  return (
    <ColumnWrapper>
      <S.NewMilestoneHeaderWrapper>
        <Heading1>New milestone</Heading1>
        <p>Create a new milestone to help organize your issues.</p>
      </S.NewMilestoneHeaderWrapper>
      <MilestoneForm type="create" />
    </ColumnWrapper>
  );
};

export default NewMilestone;
