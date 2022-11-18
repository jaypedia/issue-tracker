import { DARK, LIGHT, ThemeType } from '@/styles/theme';

const useSwitchTheme = (
  theme: ThemeType,
  setTheme: React.Dispatch<React.SetStateAction<ThemeType>>,
) => {
  const isLight = theme === LIGHT;

  const switchTheme = () => {
    const nextTheme = theme === LIGHT ? DARK : LIGHT;
    setTheme(nextTheme);
  };

  return { isLight, switchTheme };
};

export default useSwitchTheme;
