import { useState } from 'react';

import DetailsMenu from './DetailsMenu';
import * as S from './style';

import { DropDownProps } from '@/components/common/DropDown/type';
import { INDICATOR } from '@/constants/constants';
import ArrowIcon from '@/icons/DropDownArrow';
import SettingIcon from '@/icons/Setting';

const DropDown = ({
  indicatorType,
  indicatorTitle,
  menuPosition,
  detailsMenuList,
  hasCheckBox,
  checkType,
}: DropDownProps) => {
  const [hasBefore, setHasBefore] = useState(false);

  const handleDropDownClick = () => {
    if (hasBefore) {
      setHasBefore(false);
    } else {
      setHasBefore(true);
    }
  };

  return (
    <S.DropDown onClick={handleDropDownClick}>
      <S.Indicator indicatorType={indicatorType} hasBefore={hasBefore}>
        {indicatorType === INDICATOR.setting ? (
          <S.TitleWrapper>
            <S.Title>{indicatorTitle}</S.Title>
            <SettingIcon />
          </S.TitleWrapper>
        ) : (
          <>
            {indicatorTitle}
            <ArrowIcon />
          </>
        )}
      </S.Indicator>
      <DetailsMenu
        indicatorType={indicatorType}
        menuPosition={menuPosition}
        detailsMenuList={detailsMenuList}
        hasCheckBox={hasCheckBox}
        checkType={checkType}
      />
    </S.DropDown>
  );
};

export default DropDown;
