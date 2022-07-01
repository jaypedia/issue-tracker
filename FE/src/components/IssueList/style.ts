import styled from 'styled-components';

import { FONT_MIXIN, mixins } from '@/styles/mixins';
import { DARK } from '@/styles/theme';

const IssueListContainer = styled.div`
  margin: 24px 0;
  border: 1px solid ${({ theme: { color } }) => color.line};
  border-radius: 16px;
  overflow: hidden;
`;

const IssueListHeader = styled.div`
  ${mixins.flexBox({ justifyContent: 'space-between' })};
  padding: 16px 32px;
  ${FONT_MIXIN.small(400)}
  color: ${({ theme: { color } }) => color.cell.font.initial};
  background: ${({ theme: { color } }) => color.cell.bg.cellHeaderBg};
`;

const Flex = styled.div`
  ${mixins.flexBox({})}
`;

const CheckBoxIcon = styled.div`
  align-self: flex-start;
  margin-right: 20px;

  input {
    ${({ theme }) => theme === DARK && 'color-scheme: dark;'};
  }
`;

const Tabs = styled.div`
  ${mixins.flexBox({})}
`;

const TabItem = styled.button`
  ${mixins.flexBox({})}
  margin-right: 24px;
  gap: 8px;
  ${FONT_MIXIN.small(400)}

  :hover {
    color: ${({ theme: { color } }) => color.cell.font.active};
  }
`;

const ListFilter = styled.div`
  ${mixins.flexBox({})}
`;

const ListFilterItem = styled.div`
  margin-left: 32px;
`;

const EmptyList = styled.div`
  padding: 20px 0;
  border-top: 1px solid ${({ theme: { color } }) => color.line};
  ${FONT_MIXIN.large(500)}
  text-align: center;
`;

export {
  IssueListContainer,
  IssueListHeader,
  Flex,
  CheckBoxIcon,
  Tabs,
  TabItem,
  ListFilter,
  ListFilterItem,
  EmptyList,
};
