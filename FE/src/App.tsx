import { useState } from 'react';
import { QueryClientProvider, QueryClient } from 'react-query';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import { ThemeProvider } from 'styled-components';

import ThemeSwitch from '@/components/ThemeSwitch';
import Layout from '@/layout';
import Home from '@/pages/Home';
import IssueDetail from '@/pages/IssueDetail';
import Login from '@/pages/Login';
import LoginCallback from '@/pages/LoginCallback';
import NewIssue from '@/pages/NewIssue';
import NotFound from '@/pages/NotFound';
import GlobalStyle from '@/styles/GlobalStyle';
import { DARK, LIGHT } from '@/styles/theme';
import { getDefaultTheme } from '@/utils/mode';

const queryClient = new QueryClient();

const App = () => {
  const [theme, setTheme] = useState(getDefaultTheme());
  const isLight = theme === LIGHT;

  const switchTheme = () => {
    const nextTheme = theme === LIGHT ? DARK : LIGHT;
    setTheme(nextTheme);
  };

  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <RecoilRoot>
          <Router>
            <Routes>
              <Route path="/" element={<Layout />}>
                <Route index element={<Home />} />
                <Route path="new-issue" element={<NewIssue />} />
                <Route path="issue/:id" element={<IssueDetail />} />
              </Route>
              <Route path="callback" element={<LoginCallback />} />
              <Route path="login" element={<Login />} />
              <Route path="*" element={<NotFound />} />
            </Routes>
          </Router>
        </RecoilRoot>
        <ThemeSwitch switchTheme={switchTheme} isLight={isLight} />
      </ThemeProvider>
    </QueryClientProvider>
  );
};

export default App;
