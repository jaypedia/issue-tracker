import styled from 'styled-components';

import { mixins, FONT_MIXIN } from '../../../styles/mixins';

const ContentsHeaderWrapper = styled.div`
  ${mixins.flexBox({})}
  width: 100%;
  border-bottom: 1px solid ${({ theme: { color } }) => color.line};
  padding: 32px;
`;

const ContentsTopBox = styled.div`
  width: 100%;
  ${mixins.flexBox({ justifyContent: 'space-between' })}
`;

const ContentsBottomBox = styled.div``;

const Heading = styled.h1`
  font-size: ${FONT_MIXIN.display(400)};
  margin: 0;
`;

export { ContentsHeaderWrapper, ContentsTopBox, ContentsBottomBox, Heading };
