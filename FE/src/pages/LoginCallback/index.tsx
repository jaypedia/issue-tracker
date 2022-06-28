import axios from 'axios';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { setCookie } from '@/utils/cookie';

const LoginCallback = () => {
  const AUTH_URI = 'http://3.34.53.135/login/oauth/github/callback';
  const navigate = useNavigate();

  const onLogin = async () => {
    const authorizationCode = window.location.search;

    try {
      // 1) authorization code를 취득하여 get 요청 후 access & refresh token을 얻어옴
      const response = await axios.get(`${AUTH_URI}${authorizationCode}`);
      const data = await response.data;
      const { accessToken, refreshToken } = data;

      // 2) refresh token을 cookie에 저장
      setCookie('RefreshToken', refreshToken, {
        path: '/',
        secure: true,
      });

      // 3) access token을 default header로 세팅
      axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;

      // 4) Home page로 이동
      navigate('/');
    } catch (error) {
      throw new Error(`ERROR: ${error}`);
    }
  };

  useEffect(() => {
    onLogin();
  }, []);

  return <div>Loading.....</div>;
};

export default LoginCallback;
