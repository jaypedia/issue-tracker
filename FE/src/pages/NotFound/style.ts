import styled from 'styled-components';

import { mixins } from '@/styles/mixins';

const NotFoundWrapper = styled.div`
  ${mixins.flexBox({ direction: 'column' })}
  height: 100vh;
`;

const NotFoundText = styled.h1``;

export { NotFoundWrapper, NotFoundText };
