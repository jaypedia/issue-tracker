import { useState } from 'react';

import { ThemeProvider } from 'styled-components';

import Header from '@/components/Header';
import ThemeSwitch from '@/components/ThemeSwitch';
import GlobalStyle from '@/styles/GlobalStyle';
import { DARK, LIGHT } from '@/styles/theme';

const App = () => {
  const [theme, setTheme] = useState(DARK);
  const isLight = theme === LIGHT;

  const switchTheme = () => {
    const nextTheme = theme === LIGHT ? DARK : LIGHT;
    setTheme(nextTheme);
  };

  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <Header />
      <ThemeSwitch switchTheme={switchTheme} isLight={isLight} />
    </ThemeProvider>
  );
};

export default App;
