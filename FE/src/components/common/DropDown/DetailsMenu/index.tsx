import * as S from '../style';

import { DetailsMenuProps } from '@/components/common/DropDown/type';
import UserProfile from '@/components/common/UserProfile';
import useSideBar from '@/hooks/useSideBar';

type DetailsCheckMenuItemProps = {
  menu: string;
  checkType?: 'checkBox' | 'radio';
  image?: string;
  backgroundColor?: string;
  onClick: () => void;
};

const DetailsCheckMenuItem = ({
  menu,
  checkType,
  image,
  backgroundColor,
  onClick,
}: DetailsCheckMenuItemProps) => {
  const randomId = Math.random().toString();

  return (
    <S.DetailsMenuItem indicatorType="setting">
      <input type={checkType} id={randomId} name={checkType} />
      <S.CheckLabel htmlFor={randomId} onClick={onClick}>
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
  title,
  indicatorTitle,
  menuPosition,
  detailsMenuList,
  checkType,
}: DetailsMenuProps) => {
  const { handleMenuClick, menuList } = useSideBar(indicatorTitle, detailsMenuList);

  type MenuListType = {
    id: number;
    name: string;
    image: string;
    backgroundColor: string;
  };

  return (
    <S.DetailsMenu menuPosition={menuPosition} indicatorType="setting">
      <div>
        <S.DetailsMenuTitleWrapper>
          <S.DetailsMenuTitle indicatorType="setting">{title}</S.DetailsMenuTitle>
        </S.DetailsMenuTitleWrapper>
        <ul>
          {menuList.map(({ id, name, image, backgroundColor }: MenuListType) => (
            <DetailsCheckMenuItem
              key={id}
              menu={name}
              image={image}
              backgroundColor={backgroundColor}
              checkType={checkType}
              onClick={() => handleMenuClick(id)}
            />
          ))}
        </ul>
      </div>
    </S.DetailsMenu>
  );
};

export default DetailsMenu;
