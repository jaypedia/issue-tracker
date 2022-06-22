import * as S from './style';

import { UserProfileProps } from '@/type/userProfile.type';

const UserProfile = ({ imgUrl, userId, size }: UserProfileProps) => {
  return <S.UserProfile src={imgUrl} alt={userId} size={size} />;
};

export default UserProfile;
