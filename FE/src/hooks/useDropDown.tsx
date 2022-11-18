import { useState, useEffect } from 'react';

import { fetchAPI } from '@/apis/common';
import { Assignee } from '@/types/issueTypes';
import { LabelDataType } from '@/types/labelTypes';
import { MilestoneDataType } from '@/types/milestoneTypes';

type DropDownDataType = Assignee[] | MilestoneDataType | LabelDataType | undefined;

const useDropDown = (api: string) => {
  const [isBackgroundClickable, setIsBackgroundClickable] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const [dropdownData, setDropdownData] = useState<DropDownDataType>(undefined);
  const [isLoading, setIsLoading] = useState(true);

  const handleDropDownClick = (e: React.MouseEvent) => {
    e.preventDefault();
    setIsOpen(!isOpen);
    if (isBackgroundClickable) {
      setIsBackgroundClickable(false);
    } else {
      setIsBackgroundClickable(true);
    }
  };

  useEffect(() => {
    const fetchFilters = async () => {
      if (!api) return;
      const data = await fetchAPI<DropDownDataType>(api);
      setDropdownData(data);
      setIsLoading(false);
    };
    if (isOpen) {
      fetchFilters();
    }
  }, [isOpen]);

  return {
    isBackgroundClickable,
    isOpen,
    handleDropDownClick,
    dropdownData,
    isLoading,
  };
};

export default useDropDown;
