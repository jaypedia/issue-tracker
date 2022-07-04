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

const ContentsWrapper = styled.div`
  ${mixins.flexBox({ alignItems: 'flex-start', justifyContent: 'space-between' })}
  width: 100%;
  gap: 40px;
`;

const CommentsConatiner = styled.div`
  width: 100%;
`;

export { IssueDetailHeaderWrapper, ButtonBox, ContentsWrapper, CommentsConatiner };
