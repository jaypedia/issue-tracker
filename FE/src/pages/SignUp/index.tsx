import React, { useState } from 'react';

import { createAccount } from '@/apis/loginApi';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import UserProfile from '@/components/common/UserProfile';
import { USER_DEFAULT_IMG } from '@/constants/constants';
import useMovePage from '@/hooks/useMovePage';
import Logo from '@/icons/Logo';
import * as S from '@/pages/Login/style';
import { Heading4 } from '@/styles/common';

const SignUp = () => {
  const [goLogin] = useMovePage('/login');
  const [imgUrl, setImgUrl] = useState(USER_DEFAULT_IMG);

  const handleCancelClick = () => {
    goLogin();
  };

  const handleCreateAccount = (e: React.FormEvent) => {
    e.preventDefault();
    const formData = e.target;
    const email = formData.email.value;
    const name = formData.name.value;
    const password = formData.password.value;
    const profileImageUrl = formData.profileImageUrl.value;
    if (!email || !name || !password || !profileImageUrl) {
      alert('Please fill all required fields');
      return;
    }
    const userData = { name, password, email, profileImageUrl };
    createAccount(userData);
    alert(`Hello ${name}! Now you can log in with your account.`);
    goLogin();
  };

  const handleImageChange = e => {
    setImgUrl(e.target.value);
  };

  return (
    <S.LoginWrapper>
      <Logo />
      <Heading4>Create your Issue Tracker Account</Heading4>
      <UserProfile imgUrl={imgUrl} userId="new User" size="signUp" />
      <S.LoginForm onSubmit={handleCreateAccount}>
        <Input
          type="text"
          placeholder="Your Profile Image Link"
          title="User Profile Image"
          inputStyle="large"
          name="profileImageUrl"
          onChange={handleImageChange}
        />
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
