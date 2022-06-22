import styled from 'styled-components';

import { mixins } from '@/styles/mixins';

const SideBarContainer = styled.div`
  ${mixins.flexBox({ direction: 'column' })}
  border-radius: 16px;
  width: 300px;
`;

const SideBarList = styled.ul`
  width: 100%;
`;

const SideBarItemWrapper = styled.li`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
  color: ${({ theme: { color } }) => color.lightText};
  border-bottom: 1px solid ${({ theme: { color } }) => color.inputBg};
  width: 100%;
  padding: 30px 0;
  cursor: pointer;

  :hover > * {
    color: ${({ theme: { color } }) => color.primary[200]};
  }
`;

const SideBarItemText = styled.div``;

export { SideBarContainer, SideBarList, SideBarItemWrapper, SideBarItemText };
