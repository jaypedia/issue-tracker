import styled from 'styled-components';

import { FONT_MIXIN } from '@/styles/mixins';

const EmptyList = styled.div`
  padding: 20px 0;
  border-top: 1px solid ${({ theme: { color } }) => color.line};
  ${FONT_MIXIN.large(500)}
  text-align: center;
`;

export { EmptyList };
