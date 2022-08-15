import { useRef, FormEvent } from 'react';
import { useRecoilState } from 'recoil';

import * as S from './style';

import { useInput } from '@/hooks/useInput';
import { Search } from '@/icons/Search';
import { issueStatusState } from '@/stores/atoms/issue';
import { changeFilterToInputQuery } from '@/utils/issue';

const SearchInput = () => {
  const inputRef = useRef<HTMLInputElement>(null);
  const [issueState, setIssueState] = useRecoilState(issueStatusState);
  const { value, setValue, onChange } = useInput(changeFilterToInputQuery(issueState));

  const handleSearchSubmit = (e: FormEvent) => {
    e.preventDefault();
    const title = inputRef.current?.value;
    const newFilter = { ...issueState, title };
    setValue(changeFilterToInputQuery(newFilter));
    setIssueState(newFilter);
  };

  return (
    <S.SearchForm onSubmit={handleSearchSubmit}>
      <input
        title="Search Input"
        placeholder="Search issues"
        type="text"
        name="search"
        ref={inputRef}
        value={value}
        onChange={onChange}
      />
      <Search />
    </S.SearchForm>
  );
};

export default SearchInput;
