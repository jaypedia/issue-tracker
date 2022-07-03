import * as S from './style';

import Button from '@/components/common/Button';
import TabLink from '@/components/TabLink';

type NavbarPropsType = {
  btnText: string;
};

const Navbar = ({ btnText }: NavbarPropsType) => {
  return (
    <S.NavbarContainer>
      <TabLink />
      <Button btnSize="small" btnColor="primary" text={btnText} />
    </S.NavbarContainer>
  );
};

export default Navbar;
