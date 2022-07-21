import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import { REFRESH_TOKEN, REFRESH_TOKEN_OPTIONS } from '@/constants/login';
import { userState } from '@/stores/atoms/user';
import { setCookie } from '@/utils/cookie';

const useLogin = () => {
  const navigate = useNavigate();
  const setUserState = useSetRecoilState(userState);

  const onLogin = async (authorizationCode: string) => {
    try {
      // 1) authorization code를 취득하여 get 요청 후 access & refresh token을 얻어옴
      const response = await axios.get(`${process.env.OAUTH_URL_GITHUB}${authorizationCode}`);
      const data = await response.data;
      const { name, email, accessToken, refreshToken, profileImageUrl } = data;
      setUserState({ name, email, profileImageUrl });

      // 2) refresh token을 cookie에 저장
      setCookie(REFRESH_TOKEN, refreshToken, REFRESH_TOKEN_OPTIONS);

      // 3) access token을 default header로 세팅
      axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
    } catch (error) {
      throw new Error(`LOGIN ERROR: ${error}`);
    } finally {
      // 4) Homepage로 이동
      navigate('/');
    }
  };
  return onLogin;
};

export default useLogin;
