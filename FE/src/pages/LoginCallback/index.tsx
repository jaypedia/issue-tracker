import axios from 'axios';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import Loading from '@/components/common/Loading';
import { userState } from '@/stores/atoms/user';
import { setCookie } from '@/utils/cookie';

const LoginCallback = () => {
  const AUTH_URI = 'http://3.34.53.135/login/oauth/github';
  const navigate = useNavigate();
  const setUserState = useSetRecoilState(userState);

  const onLogin = async () => {
    const authorizationCode = window.location.search;

    try {
      // 1) authorization code를 취득하여 get 요청 후 access & refresh token을 얻어옴
      const response = await axios.get(`${AUTH_URI}${authorizationCode}`);
      const data = await response.data;
      const { name, email, accessToken, refreshToken, profileImageUrl } = data;
      setUserState({
        name,
        email,
        profileImageUrl,
      });

      // 2) refresh token을 cookie에 저장
      setCookie('RefreshToken', refreshToken, {
        path: '/',
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

  return <Loading />;
};

export default LoginCallback;
