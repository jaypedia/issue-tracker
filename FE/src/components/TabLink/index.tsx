import * as S from './style';

import { Label } from '@/icons/Label';
import { Milestone } from '@/icons/Milestone';

const LabelTab = () => {
  return (
    <S.TabItem>
      <Label />
      <p>Label</p>
      <S.Count>(3)</S.Count>
    </S.TabItem>
  );
};

const MilestoneTab = () => {
  return (
    <S.TabItem>
      <Milestone />
      <p>Milestones</p>
      <S.Count>(1)</S.Count>
    </S.TabItem>
  );
};

const TabLink = () => {
  return (
    <S.TabLink>
      <S.Link to="/label">
        <LabelTab />
      </S.Link>
      <S.Link to="/milestones">
        <MilestoneTab />
      </S.Link>
    </S.TabLink>
  );
};

export default TabLink;
