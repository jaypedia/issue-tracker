import * as S from './style';

import CustomLink from '@/components/common/CustomLink';
import UserProfile from '@/components/common/UserProfile';
import Logo from '@/icons/Logo';

const Header = () => {
  return (
    <S.HeaderWrapper>
      <S.InnerFlex>
        <CustomLink path="/" component={<Logo />} />
        <UserProfile
          imgUrl="https://avatars.githubusercontent.com/u/85419343?s=80&v=4"
          userId="jaypeida"
          size="large"
        />
      </S.InnerFlex>
    </S.HeaderWrapper>
  );
};

export default Header;
