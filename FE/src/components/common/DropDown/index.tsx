import DetailsMenu from './DetailsMenu';
import * as S from './style';

import { DropDownArrow } from '@/icons/DropDownArrow';
import { DropDownProps } from '@/type/dropDown.type';

const DropDown = ({
  indicatorSize,
  indicatorTitle,
  menuPosition,
  detailsMenuList,
}: DropDownProps) => {
  return (
    <S.DropDown>
      <S.Indicator indicatorSize={indicatorSize}>
        {indicatorTitle}
        <DropDownArrow />
      </S.Indicator>
      <DetailsMenu menuPosition={menuPosition} detailsMenuList={detailsMenuList} />
    </S.DropDown>
  );
};

export default DropDown;
