import * as S from './style';

import { UserProfileProps } from '@/components/common/UserProfile/type';
import { USER_DEFAULT_IMG } from '@/constants/constants';

const UserProfile = ({ imgUrl, userId, size, onClick }: UserProfileProps) => {
  return (
    <S.UserProfile src={imgUrl || USER_DEFAULT_IMG} alt={userId} size={size} onClick={onClick} />
  );
};

export default UserProfile;
