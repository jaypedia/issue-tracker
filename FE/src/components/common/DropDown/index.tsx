import DetailsMenu from './DetailsMenu';
import * as S from './style';

import { DropDownProps } from '@/components/common/DropDown/type';
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
  return (
    <S.DropDown>
      <S.Indicator indicatorType={indicatorType}>
        {indicatorType === 'setting' ? (
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
