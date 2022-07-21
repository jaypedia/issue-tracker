import { Cookies } from 'react-cookie';

const cookies = new Cookies();

type CookieOptionType = {
  path: string;
  secure?: boolean;
};

export const setCookie = (name: string, value: string, option?: CookieOptionType) => {
  return cookies.set(name, value, { ...option });
};

export const getCookie = (name: string) => {
  return cookies.get(name);
};
