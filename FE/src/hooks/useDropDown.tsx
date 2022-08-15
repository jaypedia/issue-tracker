import { useState } from 'react';

const useDropDown = () => {
  const [isBackgroundClickable, setIsBackgroundClickable] = useState(false);
  const [isOpen, setIsOpen] = useState(false);

  const handleDropDownClick = (e: React.MouseEvent) => {
    e.preventDefault();
    setIsOpen(!isOpen);
    if (isBackgroundClickable) {
      setIsBackgroundClickable(false);
    } else {
      setIsBackgroundClickable(true);
    }
  };

  return {
    isBackgroundClickable,
    isOpen,
    handleDropDownClick,
  };
};

export default useDropDown;
