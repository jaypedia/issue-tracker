import styled, { css } from 'styled-components';

import { mixins, FONT_MIXIN } from './mixins';

const MainWrapper = styled.main`
  padding: 32px 0;
`;

const InnerContainer = styled.div`
  max-width: 1280px;
  width: calc(100% - 60px);
  margin: 0 auto;
`;

const ColumnWrapper = styled(InnerContainer)`
  ${mixins.flexBox({ direction: 'column' })}
  padding: 32px 0;
`;

const headerWrapperStyle = css`
  width: 100%;
  border-bottom: 1px solid ${({ theme: { color } }) => color.line};
  padding: 32px 0;
  margin-bottom: 30px;
`;

const Heading1 = styled.h1`
  font-size: ${FONT_MIXIN.display(400)};
  margin: 0;
`;

const FlexBetween = styled.div`
  width: 100%;
  ${mixins.flexBox({ justifyContent: 'space-between' })}
`;

const FlexStart = styled.div`
  width: 100%;
  ${mixins.flexBox({ justifyContent: 'flex-start' })}
`;

const FlexColumn = styled.div`
  width: 100%;
  ${mixins.flexBox({ direction: 'column' })}
`;

export {
  MainWrapper,
  InnerContainer,
  ColumnWrapper,
  headerWrapperStyle,
  Heading1,
  FlexBetween,
  FlexStart,
  FlexColumn,
};
