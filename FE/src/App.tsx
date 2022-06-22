import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';

import ThemeSwitch from '@/components/ThemeSwitch';
import Layout from '@/layout';
import Home from '@/pages/Home';
import Login from '@/pages/Login';
import NotFound from '@/pages/NotFound';
import GlobalStyle from '@/styles/GlobalStyle';
import { DARK, LIGHT } from '@/styles/theme';
import { getDefaultTheme } from '@/utils/mode';

const App = () => {
  const [theme, setTheme] = useState(getDefaultTheme());
  const isLight = theme === LIGHT;

  const switchTheme = () => {
    const nextTheme = theme === LIGHT ? DARK : LIGHT;
    setTheme(nextTheme);
  };

  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <Router>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<Home />} />
          </Route>
          <Route path="login" element={<Login />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </Router>
      <ThemeSwitch switchTheme={switchTheme} isLight={isLight} />
    </ThemeProvider>
  );
};

export default App;
