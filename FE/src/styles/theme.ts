import { color } from './color';
import { font } from './font';

const defaultTheme = {
  font,
};

const darkThemeColors = {
  headerBg: color.grey[600],
  bg: color.black,
  cellBg: color.grey[500],
  text: color.white,
  primary: color.primary[200],
};

const lightThemeColors = {
  headerBg: color.grey[100],
  bg: color.white,
  cellBg: color.white,
  text: color.black,
  primary: color.primary[200],
};

export const DARK = {
  ...defaultTheme,
  color: darkThemeColors,
};

export const LIGHT = {
  ...defaultTheme,
  color: lightThemeColors,
};

export type ThemeColorsType = typeof darkThemeColors;
