import { useState } from 'react';
import { useRecoilValue } from 'recoil';

import * as S from './style';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import UserProfile from '@/components/common/UserProfile';
import useMovePage from '@/hooks/useMovePage';
import { LoginForm } from '@/pages/Login/style';
import { userState } from '@/stores/atoms/user';
import { Heading1, Heading4 } from '@/styles/common';

const My = () => {
  const user = useRecoilValue(userState);
  const [goLogin] = useMovePage('/login');
  const [imgUrl, setImgUrl] = useState(user?.profileImageUrl);

  const handleLogoutClick = () => {
    goLogin();
  };

  const handleImageChange = e => {
    setImgUrl(e.target.value);
  };

  return (
    <S.MyWrapper>
      <Heading1>My page</Heading1>
      <Heading4>Update or check your profile</Heading4>
      <UserProfile imgUrl={imgUrl} userId="new User" size="signUp" />
      <LoginForm>
        <Input
          type="text"
          placeholder="Your Profile Image Link"
          title="User Profile Image"
          inputStyle="large"
          name="profileImageUrl"
          defaultValue={user?.profileImageUrl}
          onChange={handleImageChange}
        />
        <Input
          type="text"
          placeholder="Your Email"
          title="User Email"
          inputStyle="large"
          name="email"
          value={user?.email}
          readOnly
        />
        <Input
          type="text"
          placeholder="Your Name"
          title="User Name"
          inputStyle="large"
          name="name"
          defaultValue={user?.name}
        />
        <Input
          type="password"
          placeholder="Your Password"
          title="Password"
          inputStyle="large"
          name="password"
        />
        <Button size="large" type="submit" color="primary" text="Update profile" />
      </LoginForm>
      <Button isText text="Logout" textColor="grey" onClick={handleLogoutClick} />
    </S.MyWrapper>
  );
};

export default My;
