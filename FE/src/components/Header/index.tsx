import * as S from './style';

import CustomLink from '@/components/common/CustomLink';
import Logo from '@/icons/Logo';
import * as I from '@/icons/UserProfile';

const Header = () => {
  return (
    <S.HeaderWrapper>
      <S.InnerFlex>
        <CustomLink path="/" component={<Logo />} />
        <I.UserProfile />
      </S.InnerFlex>
    </S.HeaderWrapper>
  );
};

export default Header;
