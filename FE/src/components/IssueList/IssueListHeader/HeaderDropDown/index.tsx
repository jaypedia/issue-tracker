import { useState, useEffect } from 'react';

import DetailsMenu from './DetailsMenu';

import { fetchAPI } from '@/apis/common';
import * as S from '@/components/common/DropDown/style';
import Loading from '@/components/common/Loading';
import useDropDown from '@/hooks/useDropDown';
import ArrowIcon from '@/icons/DropDownArrow';
import { ApiType } from '@/types/issueTypes';

type HeaderDropDownProps = {
  indicator: string;
  title: string;
  filter: string;
  api: ApiType;
};

const HeaderDropDown = ({ list }: HeaderDropDownProps) => {
  const { isBackgroundClickable, isOpen, handleDropDownClick } = useDropDown();
  const [filterData, setFilterData] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchFilters = async () => {
      const data = await fetchAPI(list.api);
      setFilterData(data);
      setIsLoading(false);
    };
    if (isOpen) {
      fetchFilters();
    }
  }, [isOpen]);

  return (
    <S.DropDown open={isOpen} onClick={handleDropDownClick}>
      <S.Indicator indicatorType="small" hasBefore={isBackgroundClickable}>
        {list.indicator}
        <ArrowIcon />
      </S.Indicator>
      {isLoading ? (
        <Loading />
      ) : (
        <DetailsMenu title={list.title} filter={list.filter} data={filterData} />
      )}
    </S.DropDown>
  );
};

export default HeaderDropDown;
