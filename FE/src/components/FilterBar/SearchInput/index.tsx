import { useRef, useEffect } from 'react';
import { useRecoilState, useSetRecoilState } from 'recoil';

import * as S from './style';

import { useInput } from '@/hooks/useInput';
import CloseButton from '@/icons/CloseButton';
import { Search } from '@/icons/Search';
import { issueStatusState, initialIssueStatus } from '@/stores/atoms/issue';
import { changeFilterToInputQuery } from '@/utils/issue';

const SearchInput = () => {
  const inputRef = useRef<HTMLInputElement>(null);
  const [issueState, setIssueState] = useRecoilState(issueStatusState);
  const { value, setValue, onChange } = useInput(changeFilterToInputQuery(issueState));

  const handleSearchSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const title = inputRef.current?.value;
    const newFilter = { ...issueState, title };
    setValue(changeFilterToInputQuery(newFilter));
    setIssueState(newFilter);
  };

  useEffect(() => {
    setValue(changeFilterToInputQuery(issueState));
  }, [issueState]);

  const setIssueStatus = useSetRecoilState(issueStatusState);

  const clearFilter = () => {
    setIssueStatus(initialIssueStatus);
    setValue(changeFilterToInputQuery(initialIssueStatus));
  };

  return (
    <S.SearchForm onSubmit={handleSearchSubmit}>
      <Search />
      <input
        title="Search Input"
        placeholder="Search issues"
        type="text"
        name="search"
        ref={inputRef}
        value={value}
        onChange={onChange}
      />
      <CloseButton onClick={clearFilter} />
    </S.SearchForm>
  );
};

export default SearchInput;
