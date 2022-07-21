import { useState } from 'react';

const useBoolean = (initialState: boolean) => {
  const [booleanState, setBooleanState] = useState(initialState);

  const setTrue = () => setBooleanState(true);
  const setFalse = () => setBooleanState(false);
  const setToggle = () => setBooleanState(!booleanState);

  return { booleanState, setTrue, setFalse, setToggle };
};

export default useBoolean;
