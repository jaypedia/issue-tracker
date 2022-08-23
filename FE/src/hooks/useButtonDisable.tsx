import { useState } from 'react';

const useButtonDisable = () => {
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);

  const handleInputChange = ({ target }) => {
    if (target.value) {
      setIsButtonDisabled(false);
    } else {
      setIsButtonDisabled(true);
    }
  };

  return { isButtonDisabled, handleInputChange };
};

export default useButtonDisable;
