import * as S from './style';

import Button from '@/components/common/Button';
import CustomLink from '@/components/common/CustomLink';

const NotFound = () => {
  return (
    <S.NotFoundWrapper>
      <S.NotFoundText>Sorry, Page Not Found</S.NotFoundText>
      <CustomLink
        path="/"
        component={<Button btnSize="small" btnColor="primary" text="Home" changeTag="div" />}
      />
    </S.NotFoundWrapper>
  );
};

export default NotFound;
