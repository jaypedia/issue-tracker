import DropDown from '../common/DropDown';
import SearchInput from './SearchInput';
import * as S from './style';

const filterBoxDetailsMenu = {
  title: 'Filter Issues',
  menus: [
    'Open issues',
    'Closed issues',
    'Your issues',
    'Everything assigned to you',
    'Everything you commented',
  ],
};
// TODO : 체크아이콘 추가 , 체크한 메뉴만 아이콘표시

const FilterBar = () => {
  return (
    <S.FilterBar>
      <DropDown
        IndicatorsSize="large"
        IndicatorsTitle="Filter"
        menuPosition="left"
        detailsMenuList={filterBoxDetailsMenu}
      />
      <SearchInput />
    </S.FilterBar>
  );
};

export default FilterBar;
