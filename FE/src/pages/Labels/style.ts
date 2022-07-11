import styled from 'styled-components';

import { FONT_MIXIN, mixins } from '@/styles/mixins';

const LabelWrapper = styled.div`
  width: 500px;
`;

const Description = styled.p`
  width: 100%;
  color: ${({ theme: { color } }) => color.lightText};
  ${FONT_MIXIN.xSmall(400)};
`;

const TextButton = styled.button`
  :hover {
    color: ${({ theme: { color } }) => color.primary[200]};
    text-decoration: underline;
  }
`;

const ButtonsWrapper = styled.div`
  ${FONT_MIXIN.xSmall(400)};
  ${mixins.flexBox({ justifyContent: 'flex-end' })}
  color: ${({ theme: { color } }) => color.lightText};

  & > :first-child {
    margin-right: 10px;
  }
`;

export { LabelWrapper, Description, TextButton, ButtonsWrapper };
