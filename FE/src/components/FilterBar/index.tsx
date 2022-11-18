import FilterDropDown from './FilterDropDown';
import SearchInput from './SearchInput';
import * as S from './style';

const FilterBar = () => {
  return (
    <S.FilterBar>
      <FilterDropDown />
      <SearchInput />
    </S.FilterBar>
  );
};

export default FilterBar;
