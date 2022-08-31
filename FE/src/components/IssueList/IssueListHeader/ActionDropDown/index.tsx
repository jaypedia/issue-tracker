import DetailsMenu from './DetailsMenu';

import * as S from '@/components/common/DropDown/style';
import useDropDown from '@/hooks/useDropDown';
import ArrowIcon from '@/icons/DropDownArrow';

const ActionDropDown = () => {
  const { isBackgroundClickable, isOpen, handleDropDownClick } = useDropDown();

  return (
    <S.DropDown open={isOpen} onClick={handleDropDownClick}>
      <S.Indicator indicatorType="small" hasBefore={isBackgroundClickable}>
        Mark as
        <ArrowIcon />
      </S.Indicator>
      <DetailsMenu />
    </S.DropDown>
  );
};

export default ActionDropDown;
