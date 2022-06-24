import styled from 'styled-components';

import { DARK } from '@/styles/theme';

const CheckBoxIcon = styled.div`
  align-self: flex-start;
  margin-right: 20px;

  input {
    ${({ theme }) => theme === DARK && 'color-scheme: dark;'};
  }
`;

export { CheckBoxIcon };
