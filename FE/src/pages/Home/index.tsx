import axios from 'axios';
import { useQuery } from 'react-query';
import { useRecoilValue } from 'recoil';

import Loading from '@/components/common/Loading';
import HomeHeader from '@/components/HomeHeader';
import IssueList from '@/components/IssueList';
import { IssueStatusType } from '@/constants/constants';
import { issueStatusState } from '@/stores/atoms/issue';
import { InnerContainer, MainWrapper } from '@/styles/common';

const fetchAPI = (issueStatus: IssueStatusType) => {
  return axios.get(`/api/issues?issueStatus=${issueStatus}`);
};

const Home = () => {
  const issueStatus = useRecoilValue(issueStatusState);
  const { data, isLoading } = useQuery(['issueList', issueStatus], () => fetchAPI(issueStatus));

  return (
    <MainWrapper>
      <InnerContainer>
        <HomeHeader />
        {isLoading && <Loading />}
        <IssueList
          list={data?.data.issues}
          openIssueCount={data?.data.OpenIssueCount}
          closedIssueCount={data?.data.ClosedIssueCount}
        />
      </InnerContainer>
    </MainWrapper>
  );
};
export default Home;
