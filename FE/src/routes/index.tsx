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
            <PrivateRoute>
              <Home />
            </PrivateRoute>
          }
        />
        <Route
          path="new-issue"
          element={
            <PrivateRoute>
              <NewIssue />
            </PrivateRoute>
          }
        />
        <Route
          path="labels"
          element={
            <PrivateRoute>
              <Labels />
            </PrivateRoute>
          }
        />
        <Route
          path="milestones"
          element={
            <PrivateRoute>
              <Milestones />
            </PrivateRoute>
          }
        />
        <Route
          path="newMilestone"
          element={
            <PrivateRoute>
              <NewMilestone />
            </PrivateRoute>
          }
        />
        <Route
          path="issue/:id"
          element={
            <PrivateRoute>
              <IssueDetail />
            </PrivateRoute>
          }
        />
        <Route
          path="my"
          element={
            <PrivateRoute>
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
