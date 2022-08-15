import DropDown from '../common/DropDown';
import SearchInput from './SearchInput';
import * as S from './style';

const filterBoxDetailsMenu = {
  indicator: 'Filter',
  title: 'Filter Issues',
  menus: [
    { name: 'Open issues', filter: 'open' },
    { name: 'Closed issues', filter: 'closed' },
    { name: 'Your issues', filter: 'created_by/me' },
    { name: 'Everything assigned to you', filter: 'assigned_by/me' },
    { name: 'Everything you commented', filter: 'commented_by/me' },
  ],
};

// TODO : 체크아이콘 추가 , 체크한 메뉴만 아이콘표시
const FilterBar = () => {
  return (
    <S.FilterBar>
      <DropDown
        indicatorType="large"
        indicatorTitle="Filter"
        menuPosition="left"
        detailsMenuList={filterBoxDetailsMenu}
      />
      <SearchInput />
    </S.FilterBar>
  );
};

export default FilterBar;
