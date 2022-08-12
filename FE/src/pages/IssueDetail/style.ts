import styled from 'styled-components';

import { mixins } from '@/styles/mixins';

const ContentsWrapper = styled.div`
  ${mixins.flexBox({ alignItems: 'flex-start', justifyContent: 'space-between' })}
  width: 100%;
  gap: 40px;
`;

const CommentsConatiner = styled.div`
  width: 100%;
`;

export { ContentsWrapper, CommentsConatiner };
