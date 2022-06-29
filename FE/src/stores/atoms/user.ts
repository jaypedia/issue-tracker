import { atom } from 'recoil';

export const ATOM_USER_KEY = 'userState';

type UserType = {
  name: string;
  email: string;
  profileImageUrl: string;
};

// TODO: default는 null로 설정, login 성공 시 데이터 다시 설정
export const userState = atom<UserType>({
  key: ATOM_USER_KEY,
  default: {
    name: 'Millie',
    email: 'millie@gmail.com',
    profileImageUrl: 'https://www.industrialempathy.com/img/remote/ZiClJf-1920w.jpg',
  },
});
