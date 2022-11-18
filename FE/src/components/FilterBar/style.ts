import styled from 'styled-components';

import { mixins } from '@/styles/mixins';

const FilterBar = styled.div`
  ${mixins.flexBox({ justifyContent: 'flex-start', alignItems: 'strech' })}
  width: 600px;
  border: 1px solid ${({ theme: { color } }) => color.line};
  border-radius: 11px;
  background: ${({ theme: { color } }) => color.inputBg};
`;

export { FilterBar };
