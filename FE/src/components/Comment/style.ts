import styled from 'styled-components';

import { FONT_MIXIN, mixins } from '@/styles/mixins';

const CommentWraaper = styled.div`
  ${mixins.flexBox({ alignItems: 'flex-start' })}
  gap: 16px;
  margin-bottom: 24px;
`;

const CommentContainer = styled.div`
  width: 100%;
  border: 1px solid ${({ theme: { color } }) => color.line};
  border-radius: 16px;
  overflow: hidden;
`;

const CommentHeader = styled.div`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
  padding: 16px 24px;
  border-bottom: 1px solid ${({ theme: { color } }) => color.line};
  background: ${({ theme: { color } }) => color.cell.bg.cellHeaderBg};
`;

const Flex = styled.div`
  ${mixins.flexBox({})}
`;

const UserId = styled.h3`
  ${FONT_MIXIN.small(700)};
`;

const Time = styled.p`
  margin-left: 8px;
  color: ${({ theme: { color } }) => color.cell.font.initial};
`;

const EditBtn = styled.button`
  padding: 5px;
  margin-left: 20px;
  ${FONT_MIXIN.xSmall(700)}
`;

const EmojiBtn = styled.button`
  padding: 5px;
  margin-left: 15px;
  font-size: 0;
`;

const CommentContents = styled.div`
  padding: 16px 24px;
`;

export {
  CommentWraaper,
  CommentContainer,
  CommentHeader,
  Flex,
  UserId,
  Time,
  EditBtn,
  EmojiBtn,
  CommentContents,
};
