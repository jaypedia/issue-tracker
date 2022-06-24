import * as S from './style';

import DropDown from '@/components/common/DropDown';
import { detailsMenuListType } from '@/components/common/DropDown/type';

const ASSIGNEES = {
  title: 'Assign up to 10 people to this pull request',
  menus: ['Millie', 'J'],
};

const LABELS = {
  title: 'Apply labels to this pull request',
  menus: ['Design', 'Feature'],
};

const MILESTONE = {
  title: 'Set milestone',
  menus: ['Week1', 'Week2'],
};

const SIDE_BAR = [
  { title: 'Assignees', defaultContents: 'No one', detailsMenuList: ASSIGNEES },
  { title: 'Labels', defaultContents: 'None yet', detailsMenuList: LABELS },
  { title: 'Milestone', defaultContents: 'No Milestone', detailsMenuList: MILESTONE },
];

type SideBarItemProps = {
  title: string;
  defaultContents: string;
  detailsMenuList: detailsMenuListType;
  checkType: 'radio' | 'checkBox';
};

const SideBarItem = ({ title, defaultContents, detailsMenuList, checkType }: SideBarItemProps) => {
  return (
    <S.SideBarItemContainer>
      <DropDown
        indicatorType="setting"
        indicatorTitle={title}
        menuPosition="right"
        detailsMenuList={detailsMenuList}
        hasCheckBox
        checkType={checkType}
      />
      <S.Contents>{defaultContents}</S.Contents>
    </S.SideBarItemContainer>
  );
};

const SideBar = () => {
  return (
    <S.SideBarContainer>
      <S.SideBarList>
        {SIDE_BAR.map(({ title, defaultContents, detailsMenuList }) => (
          <SideBarItem
            key={title}
            title={title}
            defaultContents={defaultContents}
            detailsMenuList={detailsMenuList}
            checkType={title === 'Milestone' ? 'radio' : 'checkBox'}
          />
        ))}
      </S.SideBarList>
    </S.SideBarContainer>
  );
};

export default SideBar;
