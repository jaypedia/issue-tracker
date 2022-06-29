import axios from 'axios';
import { useQuery } from 'react-query';

import Loading from '@/components/common/Loading';
import HomeHeader from '@/components/HomeHeader';
import IssueList from '@/components/IssueList';
import { InnerContainer, MainWrapper } from '@/styles/common';

const fetchAPI = () => {
  return axios.get('/api/issues?issueStatus=open');
};

const Home = () => {
  // TODO: open/closed 클릭할 때마다 fetch 요청
  // TODO: open 탭에 있을 때 또 open 탭을 클릭 시에는 fetch 요청 X

  const { data: issues, isLoading } = useQuery('issueList', fetchAPI);

  return (
    <MainWrapper>
      <InnerContainer>
        <HomeHeader />
        {isLoading && <Loading />}
        <IssueList list={issues?.data.issues} />
      </InnerContainer>
    </MainWrapper>
  );
};
export default Home;
