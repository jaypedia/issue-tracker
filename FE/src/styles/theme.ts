import { color } from './color';

export interface ColorType {
  headerBg: string;
  bg: string;
  cellBg: string;
  text: string;
  primary: string;
}

interface ThemeType {
  color: ColorType;
}

export const DARK: ThemeType = {
  color: {
    headerBg: color.grey[600],
    bg: color.black,
    cellBg: color.grey[500],
    text: color.white,
    primary: color.primary[200],
  },
};

export const LIGHT: ThemeType = {
  color: {
    headerBg: color.grey[100],
    bg: color.white,
    cellBg: color.white,
    text: color.black,
    primary: color.primary[200],
  },
};
