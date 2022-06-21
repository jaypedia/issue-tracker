import styled from 'styled-components';

import { mixins } from './mixins';

const MainWrapper = styled.main`
  padding: 32px 0;
`;

const InnerContainer = styled.div`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
  max-width: 1280px;
  width: calc(100% - 60px);
  margin: 0 auto;
`;

export { MainWrapper, InnerContainer };
