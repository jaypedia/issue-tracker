import { COLOR } from './color';

export interface ColorType {
  headerBg: string;
  bg: string;
  cellBg: string;
  text: string;
  primary: {
    [key: string]: string;
  };
  error: {
    [key: string]: string;
  };
  success: {
    [key: string]: string;
  };
}

interface ThemeType {
  color: ColorType;
}

export const DARK: ThemeType = {
  color: {
    headerBg: COLOR.grey[600],
    bg: COLOR.black,
    cellBg: COLOR.grey[500],
    text: COLOR.white,
    primary: COLOR.primary,
    error: COLOR.error,
    success: COLOR.success,
  },
};

export const LIGHT: ThemeType = {
  color: {
    headerBg: COLOR.grey[100],
    bg: COLOR.white,
    cellBg: COLOR.white,
    text: COLOR.black,
    primary: COLOR.primary,
    error: COLOR.error,
    success: COLOR.success,
  },
};
