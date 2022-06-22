import HomeHeader from '@/components/HomeHeader';
import { InnerContainer, MainWrapper } from '@/styles/common';

const Home = () => {
  return (
    <MainWrapper>
      <InnerContainer>
        <HomeHeader />
      </InnerContainer>
    </MainWrapper>
  );
};
export default Home;
