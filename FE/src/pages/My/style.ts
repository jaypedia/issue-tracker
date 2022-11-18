import styled from 'styled-components';

import { mixins } from '@/styles/mixins';

const MyWrapper = styled.div`
  ${mixins.flexBox({ direction: 'column' })}
  width: 340px;
  margin: auto;
  height: 90vh;
`;

export { MyWrapper };
