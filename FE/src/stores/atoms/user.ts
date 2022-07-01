import { atom } from 'recoil';

export const ATOM_USER_KEY = 'userState';

type UserType = {
  name: string;
  email: string;
  profileImageUrl: string;
};

export const userState = atom<UserType | null>({
  key: ATOM_USER_KEY,
  default: null,
});
