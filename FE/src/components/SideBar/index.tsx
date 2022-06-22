import * as S from './style';

import SettingIcon from '@/icons/Setting';

const SIDE_BAR = [
  { title: 'Assignees', defaultContents: 'No one' },
  { title: 'Labels', defaultContents: 'None yet' },
  { title: 'Milestone', defaultContents: 'No Milestone' },
];

type SideBarItemProps = {
  title: string;
  defaultContents: string;
};

const SideBarItem = ({ title, defaultContents }: SideBarItemProps) => {
  return (
    <S.SideBarItemContainer>
      <S.TitleWrapper>
        <S.Title>{title}</S.Title>
        <SettingIcon />
      </S.TitleWrapper>
      <S.Contents>{defaultContents}</S.Contents>
    </S.SideBarItemContainer>
  );
};

const SideBar = () => {
  return (
    <S.SideBarContainer>
      <S.SideBarList>
        {SIDE_BAR.map(({ title, defaultContents }) => (
          <SideBarItem key={title} title={title} defaultContents={defaultContents} />
        ))}
      </S.SideBarList>
    </S.SideBarContainer>
  );
};

export default SideBar;
