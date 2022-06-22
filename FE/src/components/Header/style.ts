import styled from 'styled-components';

import { InnerContainer } from '@/styles/common';
import { mixins } from '@/styles/mixins';

const HeaderWrapper = styled.div`
  background-color: ${({ theme: { color } }) => color.headerBg};
  padding: 15px 0;
`;

const InnerFlex = styled(InnerContainer)`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
`;

export { HeaderWrapper, InnerFlex };
