/* eslint-disable no-param-reassign */
import { useNavigate } from 'react-router-dom';

type MovePageType = (path: string | string[]) => (() => void)[];

const useMovePage: MovePageType = path => {
  const navigate = useNavigate();

  if (typeof path === 'string') {
    path = [path];
  }

  return path.map(p => () => navigate(p));
};

export default useMovePage;
