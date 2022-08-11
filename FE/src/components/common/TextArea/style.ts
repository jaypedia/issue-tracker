import styled, { css } from 'styled-components';

import { largeStyle, inputCommonStyle } from '@/components/common/Input/style';
import { FONT_MIXIN } from '@/styles/mixins';

const TextAreaWrapper = styled.div`
  width: 100%;
  background: ${({ theme: { color } }) => color.inputBg};
  border-radius: 16px;
  overflow: hidden;
`;

const commentStyle = css`
  border-bottom: 1px dashed ${({ theme: { color } }) => color.line};
  border-bottom-right-radius: unset;
  border-bottom-left-radius: unset;
  height: 340px;
`;

type TextAreaStyle = {
  usage: 'comment' | 'milestone';
};

const TextArea = styled.textarea<TextAreaStyle>`
  ${largeStyle}
  ${inputCommonStyle}
  resize: vertical;
  height: 200px;
  ${({ usage }) => usage === 'comment' && commentStyle}
`;

const InputFile = styled.input`
  display: none;
`;

const InputLabel = styled.label`
  cursor: pointer;
  display: block;
  color: ${({ theme: { color } }) => color.lightText};
  padding: 18px 24px;
`;

const TextAreaLabel = styled.label`
  ${FONT_MIXIN.xSmall(700)}
`;

export { TextAreaWrapper, TextArea, InputFile, InputLabel, TextAreaLabel };
