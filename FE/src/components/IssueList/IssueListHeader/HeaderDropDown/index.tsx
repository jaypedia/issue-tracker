import DetailsMenu from './DetailsMenu';

import * as S from '@/components/common/DropDown/style';
import Loading from '@/components/common/Loading';
import useDropDown from '@/hooks/useDropDown';
import ArrowIcon from '@/icons/DropDownArrow';
import { ApiType } from '@/types/issueTypes';
import { UniformMenuType } from '@/utils/dropdown';

type HeaderDropDownProps = {
  indicator: string;
  title: string;
  filter: UniformMenuType;
  api: ApiType;
};

const HeaderDropDown = ({ list }: { list: HeaderDropDownProps }) => {
  const { isBackgroundClickable, isOpen, handleDropDownClick, dropdownData, isLoading } =
    useDropDown(list.api);

  return (
    <S.DropDown open={isOpen} onClick={handleDropDownClick}>
      <S.Indicator indicatorType="small" hasBefore={isBackgroundClickable}>
        {list.indicator}
        <ArrowIcon />
      </S.Indicator>
      {isLoading ? (
        <Loading />
      ) : (
        <DetailsMenu title={list.title} filter={list.filter} data={dropdownData} />
      )}
    </S.DropDown>
  );
};

export default HeaderDropDown;
