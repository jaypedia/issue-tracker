import { useRecoilValue } from 'recoil';

import Loading from '@/components/common/Loading';
import HomeHeader from '@/components/HomeHeader';
import IssueList from '@/components/IssueList';
import { issueStatusState } from '@/stores/atoms/issue';
import { InnerContainer, MainWrapper } from '@/styles/common';
import { IssueDataType } from '@/types/issueTypes';
import { useIssueQuery } from '@/utils/query';

const Home = () => {
  const issueStatus = useRecoilValue(issueStatusState);
  const { data, isLoading } = useIssueQuery(issueStatus);
  const { issues, OpenIssueCount, ClosedIssueCount } = data as IssueDataType;

  return (
    <MainWrapper>
      <InnerContainer>
        <HomeHeader />
        {isLoading ? (
          <Loading />
        ) : (
          <IssueList
            list={issues}
            openIssueCount={OpenIssueCount}
            closedIssueCount={ClosedIssueCount}
          />
        )}
      </InnerContainer>
    </MainWrapper>
  );
};
export default Home;
