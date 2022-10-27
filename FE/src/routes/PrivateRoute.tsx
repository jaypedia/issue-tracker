import { useEffect } from 'react';

import { useIsLoggedIn } from '@/hooks/useIsLoggedIn';
import useMovePage from '@/hooks/useMovePage';

export const PrivateRoute = ({ children }: { children: JSX.Element }) => {
  const isLoggedIn = useIsLoggedIn();
  const [goLogin] = useMovePage('/login');

  useEffect(() => {
    if (!isLoggedIn) {
      goLogin();
    }
  }, [useIsLoggedIn]);

  return children;
};
