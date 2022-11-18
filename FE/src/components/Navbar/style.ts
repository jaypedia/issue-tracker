import styled from 'styled-components';

import { mixins } from '@/styles/mixins';

const NavbarContainer = styled.div`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
  margin-bottom: 20px;
`;

export { NavbarContainer };
