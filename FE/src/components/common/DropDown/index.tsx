import DetailsMenu from './DetailsMenu';
import * as S from './style';

import { DropDownArrow } from '@/icons/DropDownArrow';
import { DropDownProps } from '@/type/dropDown.type';

const DropDown = ({
  IndicatorsSize,
  IndicatorsTitle,
  menuPosition,
  detailsMenuList,
}: DropDownProps) => {
  return (
    <S.DropDown>
      <S.Indicators IndicatorsSize={IndicatorsSize}>
        {IndicatorsTitle}
        <DropDownArrow />
      </S.Indicators>
      <DetailsMenu menuPosition={menuPosition} detailsMenuList={detailsMenuList} />
    </S.DropDown>
  );
};

export default DropDown;
