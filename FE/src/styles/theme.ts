import { color } from './color';
import { font } from './font';

const defaultTheme = {
  font,
};

const darkThemeColors = {
  bg: color.black,
  cellBg: color.grey[500],
  text: color.white,
};

const lightThemeColors = {
  bg: color.white,
  cellBg: color.white,
  text: color.black,
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
