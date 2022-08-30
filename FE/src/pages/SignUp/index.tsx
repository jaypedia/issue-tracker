import { useNavigate } from 'react-router-dom';

import { createAccount } from '@/apis/loginApi';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import Logo from '@/icons/Logo';
import * as S from '@/pages/Login/style';
import { Heading4 } from '@/styles/common';

const SignUp = () => {
  const navigate = useNavigate();
  const handleCancelClick = () => {
    navigate('/login');
  };

  const handleCreateAccount = (e: React.FormEvent) => {
    e.preventDefault();
    const formData = e.target;
    const email = formData.email.value;
    const name = formData.name.value;
    const password = formData.password.value;
    const userData = { name, password, email };
    createAccount(userData);
    alert(`Hello ${name}! Now you can log in with your account.`);
    navigate('/login');
  };

  return (
    <S.LoginWrapper>
      <Logo />
      <Heading4>Create your Issue Tracker Account</Heading4>
      <S.LoginForm onSubmit={handleCreateAccount}>
        <Input
          type="text"
          placeholder="Your Email"
          title="User Email"
          inputStyle="large"
          name="email"
        />
        <Input
          type="text"
          placeholder="Your Name"
          title="User Name"
          inputStyle="large"
          name="name"
        />
        <Input
          type="password"
          placeholder="Your Password"
          title="Password"
          inputStyle="large"
          name="password"
        />
        <Button size="large" type="submit" color="primary" text="Create Account" />
      </S.LoginForm>
      <Button isText text="Cancel" textColor="grey" onClick={handleCancelClick} />
    </S.LoginWrapper>
  );
};

export default SignUp;
