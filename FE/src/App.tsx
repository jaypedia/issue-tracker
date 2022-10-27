import { useState } from 'react';
import { QueryClientProvider, QueryClient } from 'react-query';
import { ReactQueryDevtools } from 'react-query/devtools';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import { ThemeProvider } from 'styled-components';

import ThemeSwitch from '@/components/ThemeSwitch';
import useSwitchTheme from '@/hooks/useSwitchTheme';
import { Router } from '@/routes';
import GlobalStyle from '@/styles/GlobalStyle';
import { getDefaultTheme } from '@/utils/mode';
import '@/assets/fonts/index.css';

const queryClient = new QueryClient();

const App = () => {
  const [theme, setTheme] = useState(getDefaultTheme());
  const { isLight, switchTheme } = useSwitchTheme(theme, setTheme);

  return (
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools initialIsOpen />
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <RecoilRoot>
          <BrowserRouter>
            <Router />
          </BrowserRouter>
        </RecoilRoot>
        <ThemeSwitch switchTheme={switchTheme} isLight={isLight} />
      </ThemeProvider>
    </QueryClientProvider>
  );
};

export default App;
