import styled from 'styled-components';

import { FlexEnd } from '@/styles/common';
import { mixins } from '@/styles/mixins';

const FlexStartStyle = `
${mixins.flexBox({ alignItems: 'flex-start' })}
width: 100%;
`;

const FlexWrapper = styled.div`
  ${FlexStartStyle}
  gap: 20px;
`;

const CommentForm = styled.form`
  ${FlexStartStyle}
  gap: 40px;
`;

const CommentWrapper = styled.div`
  ${mixins.flexBox({ direction: 'column', alignItems: 'flex-end' })}
  width: 100%;
  gap: 15px;
`;

const ButtonWrapper = styled(FlexEnd)`
  & > :last-child {
    margin-left: 10px;
  }
`;

export { FlexWrapper, CommentForm, CommentWrapper, ButtonWrapper };
