import styled from 'styled-components';

import { mixins } from '@/styles/mixins';

const HomeHeader = styled.div`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
`;
const HederRight = styled.div`
  ${mixins.flexBox({})}
  gap: 16px;
`;
export { HomeHeader, HederRight };
