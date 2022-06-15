import { color } from './color';

export const DARK = {
  color: {
    headerBg: color.grey[600],
    bg: color.black,
    cellBg: color.grey[500],
    text: color.white,
    primary: color.primary[200],
  },
};

export const LIGHT = {
  color: {
    headerBg: color.grey[100],
    bg: color.white,
    cellBg: color.white,
    text: color.black,
    primary: color.primary[200],
  },
};

export type ThemeColorsType = typeof LIGHT.color;
