import axios from 'axios';
import { useState, useEffect } from 'react';

import HomeHeader from '@/components/HomeHeader';
import IssueList from '@/components/IssueList';
import { InnerContainer, MainWrapper } from '@/styles/common';

const Home = () => {
  const [issues, setIssues] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const getIssues = async () => {
    setIsLoading(true);
    const response = await axios.get('/api/issues');

    if (response.data.length) {
      setIssues(response.data);
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
