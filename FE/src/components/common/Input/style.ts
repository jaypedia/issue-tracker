import styled from 'styled-components';

import { COLOR } from '@/styles/color';
import { FONT_MIXIN } from '@/styles/mixins';

export type InputStyleProps = {
  inputStyle: 'large' | 'medium' | 'small';
};

const largeStyle = `
  padding: 18px 24px;
  border-radius:16px;
  `;
const mediumStyle = `
  padding: 14px 24px;
  border-radius:14px;
`;
const smallStyle = `
  padding: 0 24px;
  border-radius:11px;
`;

const inputStyleObj = {
  large: largeStyle,
  medium: mediumStyle,
  small: smallStyle,
};

const Input = styled.input<InputStyleProps>`
  width: 100%;
  ${({ inputStyle }) => inputStyleObj[inputStyle]}
  ${FONT_MIXIN.small(400)}
  color: ${({ theme: { color } }) => color.text};
  background: ${({ theme: { color } }) => color.inputBg};
  transition: 0.2s;

  ::placeholder {
    color: ${COLOR.grey[300]};
  }

  :focus {
    border: 1px solid ${({ theme: { color } }) => color.text};
    background: ${({ theme: { color } }) => color.bg};
  }
`;

export { Input };
