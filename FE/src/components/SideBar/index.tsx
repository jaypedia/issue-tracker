import SideBarItem from './SideBarItem';
import * as S from './style';

import { getSideBarData } from '@/constants/sideBar';
import { Assignee } from '@/types/issueTypes';
import { ILabel } from '@/types/labelTypes';
import { MilestoneType } from '@/types/milestoneTypes';

type SideBarProps = {
  assignees?: Assignee[];
  labels?: ILabel[];
  milestone?: MilestoneType[];
};

const SideBar = ({ assignees, labels, milestone }: SideBarProps) => {
  const sideBarData = getSideBarData(assignees, labels, milestone);

  return (
    <S.SideBarContainer>
      <S.SideBarList>
        {sideBarData.map(({ contents, defaultContents, list }) => (
          <SideBarItem
            key={list.indicator}
            title={list.indicator}
            contents={contents?.length ? contents : defaultContents}
            detailsMenuList={list}
            checkType={list.indicator === 'Milestone' ? 'radio' : 'checkBox'}
          />
        ))}
      </S.SideBarList>
    </S.SideBarContainer>
  );
};

export default SideBar;
