import * as S from './style';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import { LOGIN_URL } from '@/constants/login';
import Logo from '@/icons/Logo';

const Login = () => {
  return (
    <S.LoginWrapper>
      <Logo />
      <S.LoginBox>
        <a href={LOGIN_URL}>
          <Button btnSize="large" btnColor="black" text="Login with GitHub" changeTag="div" />
        </a>
        <S.OrDivider>or</S.OrDivider>
        <S.LoginForm>
          <Input type="text" placeholder="User ID" title="User ID" inputStyle="large" />
          <Input type="password" placeholder="Password" title="Password" inputStyle="large" />
          <Button type="submit" btnSize="large" btnColor="primary" text="Login" />
        </S.LoginForm>
        <S.SignUpLink to="/">New user? Sign up!</S.SignUpLink>
      </S.LoginBox>
    </S.LoginWrapper>
  );
};
export default Login;
