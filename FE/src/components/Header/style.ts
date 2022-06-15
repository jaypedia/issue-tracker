import styled from 'styled-components';

import { InnerConatiner } from '@/styles/common';
import { mixins } from '@/styles/mixins';

const HeaderWrapper = styled.div`
  background-color: ${({ theme: { color } }) => color.headerBg};
  padding: 15px 0;
`;

const InnerFlex = styled(InnerConatiner)`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
`;

const Button = styled.button``;

export { HeaderWrapper, InnerFlex, Button };
