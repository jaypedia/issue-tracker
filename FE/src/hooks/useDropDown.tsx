import { useState, useEffect } from 'react';

import { DropDownApi, fetchAPI } from '@/apis/common';

const useDropDown = (api?: DropDownApi) => {
  const [isBackgroundClickable, setIsBackgroundClickable] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const [dropdownData, setDropdownData] = useState(null);
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
      const data = await fetchAPI(api);
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
