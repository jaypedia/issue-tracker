import * as S from './style';

import SettingIcon from '@/icons/Setting';

const SIDE_BAR = ['Assignees', 'Labels', 'Milestone'];

const SideBarItem = ({ text }) => {
  return (
    <S.SideBarItemWrapper>
      <S.SideBarItemText>{text}</S.SideBarItemText>
      <SettingIcon />
    </S.SideBarItemWrapper>
  );
};

const SideBar = () => {
  return (
    <S.SideBarContainer>
      <S.SideBarList>
        {SIDE_BAR.map((v, i) => (
          <SideBarItem key={i} text={v} />
        ))}
      </S.SideBarList>
    </S.SideBarContainer>
  );
};

export default SideBar;
