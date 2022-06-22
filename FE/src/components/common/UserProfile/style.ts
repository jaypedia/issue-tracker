import styled from 'styled-components';

import { UserProfileStyle } from '@/type/userProfile.type';

const UserProfile = styled.img<UserProfileStyle>`
  width: ${({ size }) => (size === 'large' ? '44px' : '22px')};
  height: ${({ size }) => (size === 'large' ? '44px' : '22px')};
  border-radius: 50%;
  box-shadow: 0 0 0 1px rgb(72 72 72 / 50%);
`;
export { UserProfile };
