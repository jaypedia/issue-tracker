import axios from 'axios';
import { useState, useEffect } from 'react';

import HomeHeader from '@/components/HomeHeader';
import IssueList from '@/components/IssueList';
import { InnerContainer, MainWrapper } from '@/styles/common';

const Home = () => {
  // TODO: issue 상태를 전역으로 놓는다
  // TODO: open/closed 클릭할 때마다 fetch 요청
  // TODO: open 탭에 있을 때 또 open 탭을 클릭 시에는 fetch 요청 X

  const [issues, setIssues] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const getIssues = async () => {
    setIsLoading(true);
    const response = await axios.get('/api/issues?issueStatus=open');

    if (response.data) {
      setIssues(response.data.issues);
    }

    setIsLoading(false);
  };

  useEffect(() => {
    getIssues();
  }, []);
  return (
    <MainWrapper>
      <InnerContainer>
        <HomeHeader />
        {!isLoading && <IssueList list={issues} />}
      </InnerContainer>
    </MainWrapper>
  );
};
export default Home;
