import * as S from './style';

import { DetailsMenuProps } from '@/components/common/DropDown/type';
import CheckIcon from '@/icons/Check';

type DetailsMenuItemProps = {
  menu: string;
  indicatorType?: 'large' | 'small' | 'setting';
};

type DetailsCheckMenuItem = DetailsMenuItemProps & {
  checkType?: 'checkBox' | 'radio';
};

const DetailsMenuItem = ({ menu, indicatorType }: DetailsMenuItemProps) => {
  return (
    <S.DetailsMenuItem indicatorType={indicatorType}>
      <button type="button">{menu}</button>
    </S.DetailsMenuItem>
  );
};

// TODO: Input Checked 상태 저장하여 CheckIncon 렌더링 상태 변경
const DetailsCheckMenuItem = ({ menu, indicatorType, checkType }: DetailsCheckMenuItem) => {
  return (
    <S.DetailsMenuItem indicatorType={indicatorType}>
      <input type={checkType} id={menu} name={checkType} />
      <S.CheckLabel htmlFor={menu}>
        <CheckIcon />
        <S.Title>{menu}</S.Title>
      </S.CheckLabel>
    </S.DetailsMenuItem>
  );
};

const DetailsMenu = ({
  menuPosition,
  detailsMenuList,
  indicatorType,
  hasCheckBox,
  checkType,
}: DetailsMenuProps) => {
  return (
    <S.DetailsMenu menuPosition={menuPosition} indicatorType={indicatorType}>
      <div>
        <S.DetailsMenuTitleWrapper>
          <S.DetailsMenuTitle indicatorType={indicatorType}>
            {detailsMenuList.title}
          </S.DetailsMenuTitle>
        </S.DetailsMenuTitleWrapper>
        <ul>
          {detailsMenuList.menus.map(menu =>
            hasCheckBox ? (
              <DetailsCheckMenuItem
                key={menu}
                menu={menu}
                indicatorType={indicatorType}
                checkType={checkType}
              />
            ) : (
              <DetailsMenuItem key={menu} menu={menu} indicatorType={indicatorType} />
            ),
          )}
        </ul>
      </div>
    </S.DetailsMenu>
  );
};

export default DetailsMenu;
