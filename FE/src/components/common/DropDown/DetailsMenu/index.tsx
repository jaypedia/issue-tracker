import { useLocation } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import * as S from '../style';

import { editSideBar } from '@/apis/issueApi';
import { DetailsMenuProps } from '@/components/common/DropDown/type';
import UserProfile from '@/components/common/UserProfile';
import { useRefetchIssueDetail } from '@/hooks/useIssue';
import { sideBarState } from '@/stores/atoms/sideBar';
import { getUniformMenus } from '@/utils/dropdown';

type DetailsCheckMenuItemProps = {
  menu: string;
  checkType?: 'checkBox' | 'radio';
  image?: string;
  backgroundColor?: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
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

// TODO: refactor props names (title, indecatorTitle)
const DetailsMenu = ({
  title,
  indicatorTitle,
  menuPosition,
  detailsMenuList,
  checkType,
}: DetailsMenuProps) => {
  const [sideBarContent, setSideBarContent] = useRecoilState(sideBarState);
  const menuList = getUniformMenus(indicatorTitle, detailsMenuList);
  const { pathname } = useLocation();
  const issueId = Number(pathname.slice(7));
  const { mutate } = useRefetchIssueDetail(issueId);

  const handleMenuClick = (id: number) => {
    const currentMenu = menuList.find(v => v.id === id);
    const changeSideBarState = () => {
      if (sideBarContent[indicatorTitle].find(v => v.id === id)) {
        setSideBarContent(prev => {
          const filtered = prev[indicatorTitle].filter(v => v.id !== id);
          return { ...prev, [indicatorTitle]: filtered };
        });
      } else {
        if (indicatorTitle === 'Milestone') {
          setSideBarContent(prev => {
            return { ...prev, [indicatorTitle]: [currentMenu] };
          });
          return;
        }
        setSideBarContent(prev => {
          return { ...prev, [indicatorTitle]: [...prev[indicatorTitle], currentMenu] };
        });
      }
    };

    if (pathname === '/new-issue') {
      changeSideBarState();
    } else {
      const indicator = indicatorTitle.toLowerCase();
      editSideBar(issueId, indicator, currentMenu);
      mutate();
    }
  };

  return (
    <S.DetailsMenu menuPosition={menuPosition} indicatorType="setting">
      <div>
        <S.DetailsMenuTitleWrapper>
          <S.DetailsMenuTitle indicatorType="setting">{title}</S.DetailsMenuTitle>
        </S.DetailsMenuTitleWrapper>
        <ul>
          {menuList.map(({ id, name, image, backgroundColor }) => (
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
