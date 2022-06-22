import * as S from './style';

import { DetailsMenuProps } from '@/type/dropDown.type';

const DetailsMenu = ({ menuPosition, detailsMenuList }: DetailsMenuProps) => {
  return (
    <S.DetailsMenu menuPosition={menuPosition}>
      <div>
        <S.DetailsMenuTitle>{detailsMenuList.title}</S.DetailsMenuTitle>
        <ul>
          {detailsMenuList.menus.map(menu => (
            <S.DetailsMenuItem key={menu}>
              <button type="button">{menu}</button>
            </S.DetailsMenuItem>
          ))}
        </ul>
      </div>
    </S.DetailsMenu>
  );
};

export default DetailsMenu;
