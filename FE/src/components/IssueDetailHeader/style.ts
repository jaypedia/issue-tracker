import styled from 'styled-components';

import { headerWrapperStyle } from '@/styles/common';
import { mixins } from '@/styles/mixins';

const IssueDetailHeaderWrapper = styled.div`
  ${mixins.flexBox({ direction: 'column' })}
  ${headerWrapperStyle}
`;

const ButtonBox = styled.div`
  ${mixins.flexBox({})}

  & :last-child {
    margin-left: 10px;
  }
`;

export { IssueDetailHeaderWrapper, ButtonBox };
