import styled from 'styled-components';

import { FONT_MIXIN, mixins } from '@/styles/mixins';

const TabItem = styled.button<{ isCurrentTab: boolean }>`
  ${mixins.flexBox({})}
  margin-right: 24px;
  gap: 8px;
  ${FONT_MIXIN.small(400)}
  color: ${({ isCurrentTab, theme: { color } }) => isCurrentTab && color.cell.font.active};
  font-weight: ${({ isCurrentTab }) => isCurrentTab && 600};

  :hover {
    color: ${({ theme: { color } }) => color.cell.font.active};
  }
`;

export { TabItem };
