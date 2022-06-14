import * as S from './style';

import * as I from '@/icons/themeSwitch';

const ThemeSwitch = ({ switchTheme, isLight }: any) => {
  return <S.ThemeSwitch onClick={switchTheme}>{isLight ? <I.Moon /> : <I.Sun />}</S.ThemeSwitch>;
};

export default ThemeSwitch;
