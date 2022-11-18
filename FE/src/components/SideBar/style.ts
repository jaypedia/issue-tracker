import styled from 'styled-components';

import { mixins } from '@/styles/mixins';

const SideBarContainer = styled.div`
  ${mixins.flexBox({ direction: 'column' })}
  border-radius: 16px;
  width: 300px;

  & > :last-child {
    margin-top: 10px;
  }
`;

const SideBarList = styled.ul`
  width: 100%;
`;

const TitleWrapper = styled.li`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
  width: 100%;
  padding: 20px 0;
  cursor: pointer;

  :hover > * {
    color: ${({ theme: { color } }) => color.primary[200]};
  }
`;

const Title = styled.div``;

export { SideBarContainer, SideBarList, TitleWrapper, Title };
