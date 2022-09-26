import * as S from './style';

import { useGetLabel } from '@/hooks/useLabel';
import { useGetAllMilestone } from '@/hooks/useMilestone';
import { Label } from '@/icons/Label';
import { Milestone } from '@/icons/Milestone';

const LabelTab = () => {
  const { data } = useGetLabel();

  return (
    <S.TabItem>
      <Label />
      <p>Labels</p>
      <S.Count>{data?.labelCount || 0}</S.Count>
    </S.TabItem>
  );
};

const MilestoneTab = () => {
  const { data } = useGetAllMilestone();

  return (
    <S.TabItem>
      <Milestone />
      <p>Milestones</p>
      <S.Count>{data?.allMilestoneCount || 0}</S.Count>
    </S.TabItem>
  );
};

const TabLink = () => {
  return (
    <S.TabLink>
      <S.Link to="/labels">
        <LabelTab />
      </S.Link>
      <S.Link to="/milestones">
        <MilestoneTab />
      </S.Link>
    </S.TabLink>
  );
};

export default TabLink;
