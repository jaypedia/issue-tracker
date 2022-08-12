import styled from 'styled-components';

import { FONT_MIXIN, mixins } from '@/styles/mixins';

const IssueListHeader = styled.div`
  ${mixins.flexBox({ justifyContent: 'space-between' })};
  padding: 10px 0;
  ${FONT_MIXIN.small(400)}
  color: ${({ theme: { color } }) => color.cell.font.initial};
  background: ${({ theme: { color } }) => color.cell.bg.cellHeaderBg};
`;

const Tabs = styled.div`
  ${mixins.flexBox({})}
`;

const Flex = styled.div`
  ${mixins.flexBox({})}
`;

const ListFilter = styled.div`
  ${mixins.flexBox({})}
  padding-right: 20px;
`;

const ListFilterItem = styled.div`
  margin-left: 32px;
`;

export { Flex, ListFilter, ListFilterItem, IssueListHeader, Tabs };
