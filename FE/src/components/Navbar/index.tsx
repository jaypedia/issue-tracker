import * as S from './style';

import Button from '@/components/common/Button';
import TabLink from '@/components/TabLink';

type NavbarPropsType = {
  btnText: string;
  onClick: () => void;
};

const Navbar = ({ btnText, onClick }: NavbarPropsType) => {
  return (
    <S.NavbarContainer>
      <TabLink />
      <Button size="small" color="primary" text={btnText} onClick={onClick} />
    </S.NavbarContainer>
  );
};

export default Navbar;
