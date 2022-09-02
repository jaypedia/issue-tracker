import { useRecoilValue } from 'recoil';

import { userState } from '@/stores/atoms/user';

export const useIsLoggedIn = () => {
  const user = useRecoilValue(userState);
  return Boolean(user);
};
