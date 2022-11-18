import styled from 'styled-components';

import { mixins } from '@/styles/mixins';

const IssueTitleForm = styled.form`
  ${mixins.flexBox({})}
  width: 100%;

  & > :first-child {
    margin-right: 20px;
  }
`;

export { IssueTitleForm };
