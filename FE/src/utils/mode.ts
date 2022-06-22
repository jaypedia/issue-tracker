import { DARK, LIGHT } from '@/styles/theme';

export const getDefaultTheme = () => {
  const isUserSystemModeDark = window.matchMedia?.('(prefers-color-scheme: dark)').matches;
  return isUserSystemModeDark ? DARK : LIGHT;
};
