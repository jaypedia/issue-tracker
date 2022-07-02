import styled from 'styled-components';

import { FONT_MIXIN, mixins } from '@/styles/mixins';
import { DARK } from '@/styles/theme';

const IssueListContainer = styled.div`
  margin: 24px 0;
  border: 1px solid ${({ theme: { color } }) => color.line};
  border-radius: 16px;
  overflow: hidden;
`;

const Flex = styled.div`
  ${mixins.flexBox({})}
`;

const CheckBox = styled.div`
  align-self: flex-start;
  margin-right: 20px;

  input {
    ${({ theme }) => theme === DARK && 'color-scheme: dark;'};
  }
`;

const EmptyList = styled.div`
  padding: 20px 0;
  border-top: 1px solid ${({ theme: { color } }) => color.line};
  ${FONT_MIXIN.large(500)}
  text-align: center;
`;

export { IssueListContainer, Flex, CheckBox, EmptyList };
