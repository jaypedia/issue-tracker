import { useRecoilValue } from 'recoil';

import * as S from './style';

import CustomLink from '@/components/common/CustomLink';
import UserProfile from '@/components/common/UserProfile';
import Logo from '@/icons/Logo';
import { userState } from '@/stores/atoms/user';

const Header = () => {
  const userData = useRecoilValue(userState);

  return (
    <S.HeaderWrapper>
      <S.InnerFlex>
        <CustomLink path="/" component={<Logo />} />
        <UserProfile imgUrl={userData?.profileImageUrl} userId={userData?.name} size="large" />
      </S.InnerFlex>
    </S.HeaderWrapper>
  );
};

export default Header;
