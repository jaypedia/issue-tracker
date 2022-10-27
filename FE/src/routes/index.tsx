import { Routes, Route } from 'react-router-dom';

import Layout from '@/layout';
import Home from '@/pages/Home';
import IssueDetail from '@/pages/IssueDetail';
import Labels from '@/pages/Labels';
import Login from '@/pages/Login';
import LoginCallback from '@/pages/LoginCallback';
import Milestones from '@/pages/Milestones';
import My from '@/pages/My';
import NewIssue from '@/pages/NewIssue';
import NewMilestone from '@/pages/NewMilestone';
import NotFound from '@/pages/NotFound';
import SignUp from '@/pages/SignUp';
import { PrivateRoute } from '@/routes/PrivateRoute';

export const Router = () => {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route
          index
          element={
            <PrivateRoute path="/">
              <Home />
            </PrivateRoute>
          }
        />
        <Route
          path="new-issue"
          element={
            <PrivateRoute path="/new-issue">
              <NewIssue />
            </PrivateRoute>
          }
        />
        <Route
          path="labels"
          element={
            <PrivateRoute path="/labels">
              <Labels />
            </PrivateRoute>
          }
        />
        <Route
          path="milestones"
          element={
            <PrivateRoute path="/milestones">
              <Milestones />
            </PrivateRoute>
          }
        />
        <Route
          path="newMilestone"
          element={
            <PrivateRoute path="/newMilestone">
              <NewMilestone />
            </PrivateRoute>
          }
        />
        <Route path="issue/:id" element={<IssueDetail />} />
        <Route
          path="my"
          element={
            <PrivateRoute path="/my">
              <My />
            </PrivateRoute>
          }
        />
      </Route>
      <Route path="callback" element={<LoginCallback />} />
      <Route path="login" element={<Login />} />
      <Route path="sign-up" element={<SignUp />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
};
