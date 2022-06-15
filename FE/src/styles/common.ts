import styled from 'styled-components';

import { mixins } from './mixins';

const InnerConatiner = styled.div`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
  max-width: 1280px;
  width: calc(100% - 60px);
  margin: 0 auto;
`;

export { InnerConatiner };
