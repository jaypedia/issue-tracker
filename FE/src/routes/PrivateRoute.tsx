import { useEffect } from 'react';

import { useIsLoggedIn } from '@/hooks/useIsLoggedIn';
import useMaintainLogin from '@/hooks/useMaintainLogin';

export const PrivateRoute = ({ children, path }: { children: JSX.Element; path: string }) => {
  const isLoggedIn = useIsLoggedIn();
  const mainTainLogin = useMaintainLogin(path);

  useEffect(() => {
    if (!isLoggedIn) {
      mainTainLogin();
    }
  }, [useIsLoggedIn]);

  return children;
};
