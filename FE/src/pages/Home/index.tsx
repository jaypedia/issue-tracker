import HomeHeader from '@/components/HomeHeader';
import IssueList from '@/components/IssueList';
import { InnerContainer, MainWrapper } from '@/styles/common';

const Home = () => {
  return (
    <MainWrapper>
      <InnerContainer>
        <HomeHeader />
        <IssueList />
      </InnerContainer>
    </MainWrapper>
  );
};
export default Home;
