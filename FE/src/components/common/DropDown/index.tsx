import DetailsMenu from './DetailsMenu';
import * as S from './style';

import { DropDownProps } from '@/components/common/DropDown/type';
import Loading from '@/components/common/Loading';
import useDropDown from '@/hooks/useDropDown';
import SettingIcon from '@/icons/Setting';

const DropDown = ({
  indicatorType,
  indicatorTitle,
  menuPosition,
  detailsMenuList,
  hasCheckBox,
  checkType,
}: DropDownProps) => {
  const { isBackgroundClickable, isOpen, handleDropDownClick, dropdownData, isLoading } =
    useDropDown(detailsMenuList.api);

  return (
    <S.DropDown open={isOpen} onClick={handleDropDownClick}>
      <S.Indicator indicatorType={indicatorType} hasBefore={isBackgroundClickable}>
        <S.TitleWrapper>
          <S.Title>{indicatorTitle}</S.Title>
          <SettingIcon />
        </S.TitleWrapper>
      </S.Indicator>
      {isLoading ? (
        <Loading />
      ) : (
        <DetailsMenu
          title={detailsMenuList.title}
          indicatorTitle={indicatorTitle}
          indicatorType={indicatorType}
          menuPosition={menuPosition}
          detailsMenuList={dropdownData}
          hasCheckBox={hasCheckBox}
          checkType={checkType}
        />
      )}
    </S.DropDown>
  );
};

export default DropDown;
