import styled from 'styled-components';

import { DARK } from '@/styles/theme';

type LabelType = {
  isHeader?: boolean;
};

const CheckBox = styled.div`
  align-self: flex-start;
  margin-right: 18px;

  input {
    ${({ theme }) => theme === DARK && 'color-scheme: dark;'};
  }
`;

const Label = styled.label<LabelType>`
  display: block;
  padding: 8px 0 8px 16px;
  height: ${({ isHeader }) => (isHeader ? '40px' : '70px')};
`;

export { CheckBox, Label };
