import { useEffect } from 'react';

import Loading from '@/components/common/Loading';
import useLogin from '@/hooks/useLogin';

const LoginCallback = () => {
  const authorizationCode = window.location.search;
  const onLogin = useLogin();

  useEffect(() => {
    onLogin(authorizationCode);
  }, [authorizationCode]);

  return <Loading />;
};

export default LoginCallback;
