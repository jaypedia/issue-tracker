import * as S from './style';

import { Search } from '@/icons/Search';

const SearchInput = () => {
  return (
    <S.SearchInputContainer>
      <input title="Search Input" placeholder="is:issue is:open" type="text" name="search" />
      <Search />
    </S.SearchInputContainer>
  );
};

export default SearchInput;
