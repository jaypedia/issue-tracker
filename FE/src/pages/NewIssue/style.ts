import styled from 'styled-components';

import { mixins, FONT_MIXIN } from '../../styles/mixins';

import { InnerContainer } from '@/styles/common';

const NewIssueWrapper = styled(InnerContainer)`
  ${mixins.flexBox({ direction: 'column' })}
  padding: 32px 0;
`;

const NewIssueHeaderWrapper = styled.div`
  ${mixins.flexBox({ justifyContent: 'flex-start' })}
  width: 100%;
  border-bottom: 1px solid ${({ theme: { color } }) => color.line};
  padding: 32px 0;
  margin-bottom: 30px;
`;

const Heading = styled.h1`
  font-size: ${FONT_MIXIN.display(400)};
  margin: 0;
`;

export { NewIssueWrapper, NewIssueHeaderWrapper, Heading };
