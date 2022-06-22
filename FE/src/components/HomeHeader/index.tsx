import * as S from './style';

import Button from '@/components/common/Button';
import CustomLink from '@/components/common/CustomLink';
import FilterBar from '@/components/FilterBar';
import TabLink from '@/components/TabLink';

const HomeHeader = () => {
  return (
    <S.HomeHeader>
      <FilterBar />
      <S.HederRight>
        <TabLink />
        <CustomLink
          path="/new-issue"
          component={<Button btnSize="small" btnColor="primary" text="New Issue" changeTag="div" />}
        />
      </S.HederRight>
    </S.HomeHeader>
  );
};

export default HomeHeader;
