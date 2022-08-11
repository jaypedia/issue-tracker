import styled from 'styled-components';

import { FlexEnd } from '@/styles/common';

const MilestoneForm = styled.form`
  width: 100%;
`;

const ButtonWrapper = styled(FlexEnd)`
  width: 100%;
  border-top: 1px solid ${({ theme: { color } }) => color.line};
  margin: 20px 0;
  padding: 30px 0;

  & > :last-child {
    margin-left: 10px;
  }
`;

export { MilestoneForm, ButtonWrapper };
