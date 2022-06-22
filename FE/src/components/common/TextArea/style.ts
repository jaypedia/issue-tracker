import styled from 'styled-components';

import { largeStyle, inputCommonStyle } from '@/components/common/Input/style';

const TextAreaWrapper = styled.div`
  width: 100%;
  background: ${({ theme: { color } }) => color.inputBg};
  border-radius: 16px;
  overflow: hidden;
`;

const TextArea = styled.textarea`
  ${largeStyle}
  ${inputCommonStyle}
  height: 340px;
  resize: vertical;
  border-bottom: 1px dashed ${({ theme: { color } }) => color.line};
  border-bottom-right-radius: unset;
  border-bottom-left-radius: unset;
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

export { TextAreaWrapper, TextArea, InputFile, InputLabel };
