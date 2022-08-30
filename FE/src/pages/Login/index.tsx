import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import * as S from './style';

import { postLogin, MockUserDataType } from '@/apis/loginApi';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import { LOGIN_URL } from '@/constants/login';
import { useIsLoggedIn } from '@/hooks/useIsLoggedIn';
import useMaintainLogin from '@/hooks/useMaintainLogin';
import Logo from '@/icons/Logo';
import { userState } from '@/stores/atoms/user';

const Login = () => {
  const isLoggedIn = useIsLoggedIn();
  const navigate = useNavigate();
  const mainTainLogin = useMaintainLogin();
  const setUserState = useSetRecoilState(userState);

  useEffect(() => {
    if (isLoggedIn) {
      navigate('/');
    } else {
      mainTainLogin();
    }
  }, [useIsLoggedIn]);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    const formData = e.target;
    const userData = { email: formData.email.value, password: formData.password.value };
    const user = await postLogin<MockUserDataType>(userData);
    if (user && user.name) {
      navigate('/');
      setUserState({ name: user.name, email: user.email, profileImageUrl: user.profileImageUrl });
    } else {
      alert('Please sign up first!');
    }
  };

  return (
    <S.LoginWrapper>
      <Logo />
      <S.LoginBox>
        <a href={LOGIN_URL}>
          <Button size="large" color="black" text="Login with GitHub" changeTag="div" />
        </a>
        <S.OrDivider>or</S.OrDivider>
        <S.LoginForm onSubmit={handleLogin}>
          <Input
            type="text"
            placeholder="Your Email"
            title="User Email"
            inputStyle="large"
            name="email"
          />
          <Input
            type="password"
            placeholder="Password"
            title="Password"
            inputStyle="large"
            name="password"
          />
          <Button size="large" type="submit" color="primary" text="Login" />
        </S.LoginForm>
        <S.SignUpLink to="/sign-up">New user? Sign up!</S.SignUpLink>
      </S.LoginBox>
    </S.LoginWrapper>
  );
};
export default Login;
