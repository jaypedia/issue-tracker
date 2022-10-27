import { useRecoilValue } from 'recoil';

import Loading from '@/components/common/Loading';
import HomeHeader from '@/components/HomeHeader';
import IssueList from '@/components/IssueList';
import { useGetIssue } from '@/hooks/useIssue';
import { issueStatusState } from '@/stores/atoms/issue';
import { InnerContainer, MainWrapper } from '@/styles/common';

const Home = () => {
  const issueStatus = useRecoilValue(issueStatusState);
  const { data, isLoading } = useGetIssue(issueStatus);

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
