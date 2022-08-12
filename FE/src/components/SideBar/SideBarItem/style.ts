import styled from 'styled-components';

import { FlexStart } from '@/styles/common';
import { FONT_MIXIN, mixins } from '@/styles/mixins';

const SideBarItemContainer = styled.div`
  width: 100%;
  border-bottom: 1px solid ${({ theme: { color } }) => color.inputBg};
  color: ${({ theme: { color } }) => color.lightText};
  padding-bottom: 20px;
`;

const Contents = styled.div`
  width: 100%;
  ${FONT_MIXIN.xSmall(400)}
`;

const Assignee = styled.div`
  ${mixins.flexBox({ justifyContent: 'flex-start' })}
  ${FONT_MIXIN.xSmall(700)}

  & > :first-child {
    margin-right: 10px;
  }

  & > * {
    margin-bottom: 10px;
  }
`;

const LabelContainer = styled(FlexStart)`
  flex-wrap: wrap;
  & > * {
    margin-right: 5px;
    margin-bottom: 5px;
  }
`;

export { SideBarItemContainer, Contents, Assignee, LabelContainer };
