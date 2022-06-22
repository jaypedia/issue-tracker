import { COLOR } from './color';

export interface ColorType {
  headerBg: string;
  bg: string;
  inputBg: string;
  cellBg: string;
  text: string;
  lightText: string;
  line: string;
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
    inputBg: COLOR.grey[700],
    text: COLOR.white,
    lightText: COLOR.grey[300],
    line: COLOR.grey[400],
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
    inputBg: COLOR.grey[100],
    text: COLOR.black,
    lightText: COLOR.grey[400],
    line: COLOR.grey[200],
    primary: COLOR.primary,
    error: COLOR.error,
    success: COLOR.success,
  },
};
