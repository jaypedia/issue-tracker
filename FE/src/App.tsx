import { useState } from 'react';
import { QueryClientProvider, QueryClient } from 'react-query';
import { ReactQueryDevtools } from 'react-query/devtools';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import { ThemeProvider } from 'styled-components';

import ThemeSwitch from '@/components/ThemeSwitch';
import Layout from '@/layout';
import Home from '@/pages/Home';
import IssueDetail from '@/pages/IssueDetail';
import Labels from '@/pages/Labels';
import Login from '@/pages/Login';
import LoginCallback from '@/pages/LoginCallback';
import Milestones from '@/pages/Milestones';
import NewIssue from '@/pages/NewIssue';
import NewMilestone from '@/pages/NewMilestone';
import NotFound from '@/pages/NotFound';
import GlobalStyle from '@/styles/GlobalStyle';
import { DARK, LIGHT } from '@/styles/theme';
import { getDefaultTheme } from '@/utils/mode';
import '@/assets/fonts/index.css';

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
      <ReactQueryDevtools initialIsOpen />
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <RecoilRoot>
          <Router>
            <Routes>
              <Route path="/" element={<Layout />}>
                <Route index element={<Home />} />
                <Route path="new-issue" element={<NewIssue />} />
                <Route path="labels" element={<Labels />} />
                <Route path="milestones" element={<Milestones />} />
                <Route path="newMilestone" element={<NewMilestone />} />
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
