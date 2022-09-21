import { useEffect } from 'react';
import { useRecoilValue } from 'recoil';

import Loading from '@/components/common/Loading';
import HomeHeader from '@/components/HomeHeader';
import IssueList from '@/components/IssueList';
import { useIsLoggedIn } from '@/hooks/useIsLoggedIn';
import { useGetIssue } from '@/hooks/useIssue';
import useMovePage from '@/hooks/useMovePage';
import { issueStatusState } from '@/stores/atoms/issue';
import { InnerContainer, MainWrapper } from '@/styles/common';

const Home = () => {
  const issueStatus = useRecoilValue(issueStatusState);
  const { data, isLoading } = useGetIssue(issueStatus);
  const isLoggedIn = useIsLoggedIn();
  const [goLogin] = useMovePage('/login');

  useEffect(() => {
    if (!isLoggedIn) {
      goLogin();
    }
  }, [useIsLoggedIn]);

  return (
    <MainWrapper>
      <InnerContainer>
        <HomeHeader />
        {isLoading || !data ? (
          <Loading />
        ) : (
          <IssueList
            issues={data.issues}
            openIssueCount={data.openIssueCount}
            closedIssueCount={data.closedIssueCount}
          />
        )}
      </InnerContainer>
    </MainWrapper>
  );
};
export default Home;
