import SideBarItem from './SideBarItem';
import * as S from './style';

import { initialSideBarData } from '@/constants/sideBar';

const SideBar = () => {
  return (
    <S.SideBarContainer>
      <S.SideBarList>
        {initialSideBarData.map(({ defaultContents, list }) => (
          <SideBarItem
            key={list.indicator}
            title={list.indicator}
            defaultContents={defaultContents}
            detailsMenuList={list}
            checkType={list.indicator === 'Milestone' ? 'radio' : 'checkBox'}
          />
        ))}
      </S.SideBarList>
    </S.SideBarContainer>
  );
};

export default SideBar;
