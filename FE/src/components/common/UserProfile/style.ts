import styled from 'styled-components';

import { UserProfileStyle } from '@/components/common/UserProfile/type';

const ProfileSize = (size: string) => {
  switch (size) {
    case 'signUp':
      return `
        width: 100px;
        height: 100px;`;
    case 'large':
      return `
        cursor: pointer;
        width: 44px;
        height: 44px;`;
    case 'small':
      return `
          width: 22px;
          height: 22px;`;
    default:
      return null;
  }
};

const UserProfile = styled.img<UserProfileStyle>`
  ${({ size }) => ProfileSize(size)};
  border-radius: 50%;
  box-shadow: 0 0 0 1px rgb(72 72 72 / 50%);
`;
export { UserProfile };
