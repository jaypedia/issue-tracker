import SideBarItem from './SideBarItem';
import * as S from './style';

import { mockAssignees } from '@/mocks/Assignees/data';
import { mockLabels } from '@/mocks/Labels/data';
import { mockMilestones } from '@/mocks/Milestones/data';

const assingeeList = {
  indicator: 'Assignees',
  title: 'Assign up to 10 people to this issue',
  menus: mockAssignees,
};

const labelList = {
  indicator: 'Labels',
  title: 'Apply labels to this issue',
  menus: mockLabels.labels,
};

const milestoneList = {
  indicator: 'Milestone',
  title: 'Set milestone',
  menus: mockMilestones.milestones,
};

const SideBar = ({ assignees, labels, milestone }) => {
  const sideBarData = [
    { defaultContents: 'No one', list: assingeeList, contents: assignees },
    { defaultContents: 'None yet', list: labelList, contents: labels },
    { defaultContents: 'No Milestone', list: milestoneList, contents: milestone },
  ];
  return (
    <S.SideBarContainer>
      <S.SideBarList>
        {sideBarData.map(({ contents, defaultContents, list }) => (
          <SideBarItem
            key={list.indicator}
            title={list.indicator}
            contents={contents.length || Object.keys(contents).length ? contents : defaultContents}
            detailsMenuList={list}
            checkType={list.indicator === 'Milestone' ? 'radio' : 'checkBox'}
          />
        ))}
      </S.SideBarList>
    </S.SideBarContainer>
  );
};

export default SideBar;
