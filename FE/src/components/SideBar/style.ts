import styled from 'styled-components';

import { FONT_MIXIN } from '../../styles/mixins';

import { mixins } from '@/styles/mixins';

const SideBarContainer = styled.div`
  ${mixins.flexBox({ direction: 'column' })}
  border-radius: 16px;
  width: 300px;
`;

const SideBarList = styled.ul`
  width: 100%;
`;

const SideBarItemContainer = styled.div`
  width: 100%;
  border-bottom: 1px solid ${({ theme: { color } }) => color.inputBg};
  color: ${({ theme: { color } }) => color.lightText};
  padding-bottom: 20px;
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

const Contents = styled.div`
  ${FONT_MIXIN.xSmall(400)}
`;

export { SideBarContainer, SideBarList, SideBarItemContainer, TitleWrapper, Title, Contents };
