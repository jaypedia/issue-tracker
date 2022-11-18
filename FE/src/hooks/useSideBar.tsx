import { useLocation } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import { editSideBar } from '@/apis/issueApi';
import { useRefetchIssueDetail } from '@/hooks/useIssue';
import { sideBarState } from '@/stores/atoms/sideBar';
import { getUniformMenus } from '@/utils/dropdown';

export type IndicatorTitleType = 'Assignees' | 'Labels' | 'Milestone';

// TODO: TypeScript
const useSideBar = (indicatorTitle: IndicatorTitleType, detailsMenuList: any) => {
  const [sideBarContent, setSideBarContent] = useRecoilState(sideBarState);
  const menuList = getUniformMenus(indicatorTitle, detailsMenuList);
  const { pathname } = useLocation();
  const issueId = Number(pathname.slice(7));
  const { mutate } = useRefetchIssueDetail(issueId);

  const isSelectedMenu = (id: number) => {
    return sideBarContent[indicatorTitle].find(v => v.id === id);
  };

  const changeSideBarState = (currentMenu, id: number) => {
    if (isSelectedMenu(id)) {
      setSideBarContent(prev => {
        const filtered = prev[indicatorTitle].filter(v => v.id !== id);
        return { ...prev, [indicatorTitle]: filtered };
      });
      return;
    }
    if (indicatorTitle === 'Milestone') {
      setSideBarContent(prev => {
        return { ...prev, [indicatorTitle]: [currentMenu] };
      });
      return;
    }
    setSideBarContent(prev => {
      return { ...prev, [indicatorTitle]: [...prev[indicatorTitle], currentMenu] };
    });
  };

  const handleMenuClick = (id: number) => {
    const currentMenu = menuList.find(v => v.id === id);
    if (pathname === '/new-issue') {
      changeSideBarState(currentMenu, id);
    } else {
      const indicator = indicatorTitle.toLowerCase();
      editSideBar(issueId, indicator, currentMenu);
      mutate();
    }
  };

  return {
    handleMenuClick,
    menuList,
  };
};

export default useSideBar;
