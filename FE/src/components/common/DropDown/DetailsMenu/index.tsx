import * as S from '../style';

import { DetailsMenuProps } from '@/components/common/DropDown/type';
import UserProfile from '@/components/common/UserProfile';
import { getUniformMenus } from '@/utils/dropdown';

type DetailsMenuItemProps = {
  menu: string;
  indicatorType?: 'large' | 'small' | 'setting';
};

type DetailsCheckMenuItem = DetailsMenuItemProps & {
  checkType?: 'checkBox' | 'radio';
  image?: string;
  backgroundColor?: string;
};

const DetailsMenuItem = ({ menu, indicatorType }: DetailsMenuItemProps) => {
  return (
    <S.DetailsMenuItem indicatorType={indicatorType}>
      <button type="button">{menu}</button>
    </S.DetailsMenuItem>
  );
};

const DetailsCheckMenuItem = ({
  menu,
  indicatorType,
  checkType,
  image,
  backgroundColor,
}: DetailsCheckMenuItem) => {
  const randomId = Math.random().toString();
  return (
    <S.DetailsMenuItem indicatorType={indicatorType}>
      <input type={checkType} id={randomId} name={checkType} />
      <S.CheckLabel htmlFor={randomId}>
        <S.Menu>
          {image && <UserProfile imgUrl={image} userId={menu} size="small" />}
          {backgroundColor && <S.LabelColorCircle backgroundColor={backgroundColor} />}
          <p>{menu}</p>
        </S.Menu>
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
  const menuList = getUniformMenus(detailsMenuList.indicator, detailsMenuList);

  return (
    <S.DetailsMenu menuPosition={menuPosition} indicatorType={indicatorType}>
      <div>
        <S.DetailsMenuTitleWrapper>
          <S.DetailsMenuTitle indicatorType={indicatorType}>
            {detailsMenuList.title}
          </S.DetailsMenuTitle>
        </S.DetailsMenuTitleWrapper>
        <ul>
          {menuList.map(({ id, name, image, backgroundColor }) =>
            hasCheckBox ? (
              <DetailsCheckMenuItem
                key={id}
                menu={name}
                image={image}
                backgroundColor={backgroundColor}
                indicatorType={indicatorType}
                checkType={checkType}
              />
            ) : (
              <DetailsMenuItem key={id} menu={name} indicatorType={indicatorType} />
            ),
          )}
        </ul>
      </div>
    </S.DetailsMenu>
  );
};

export default DetailsMenu;
