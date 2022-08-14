import * as S from './style';

import { UserProfileProps } from '@/components/common/UserProfile/type';
import { USER } from '@/constants/constants';

const UserProfile = ({ imgUrl, userId, size }: UserProfileProps) => {
  return <S.UserProfile src={imgUrl || USER.image} alt={userId} size={size} />;
};

export default UserProfile;
