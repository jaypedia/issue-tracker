import styled from 'styled-components';

import { ButtonStyleProps } from '@/components/common/Button/type';
import { COLOR } from '@/styles/color';
import { FONT_MIXIN, mixins } from '@/styles/mixins';

// Button Sizes
const large = `
  width: 340px;
  height: 64px;
  border-radius: 20px;
  ${FONT_MIXIN.medium(700)}
`;

const medium = `
  width: 240px;
  height: 56px;
  border-radius: 20px;
  ${FONT_MIXIN.medium(700)}
`;

const small = `
  width: 120px;
  height: 40px;
  border-radius: 11px;
  ${FONT_MIXIN.xSmall(700)}
`;

const sizeObj = {
  large,
  medium,
  small,
};

// Button Colors
const primary = `
  color: ${COLOR.white};
  background-color: ${COLOR.primary[200]};

  :disabled {
    background-color: ${COLOR.primary[100]};
    opacity: 0.7;
    cursor: default;
  }

  :not(:disabled):hover {
    background-color: ${COLOR.primary[300]};
  }

  :focus {
    border: 4px solid ${COLOR.primary[100]};
  }
`;

const grey = `
  color: ${COLOR.black};
  border: 1px solid ${COLOR.grey[200]};
  background-color: ${COLOR.grey[100]};
  
  :disabled {
    color: ${COLOR.grey[400]};
    cursor: default;
  }

  :not(:disabled):hover {
    background-color: ${COLOR.grey[200]};
  }

  :focus {
    border: 2px solid ${COLOR.grey[400]};
  }
`;

const black = `
  color: ${COLOR.white};
  border: 1px solid ${COLOR.white};
  background-color: #000;
  transition: background .2s;

  :hover {
    background-color: ${COLOR.black};
  }

  :focus {
    border: 2px solid ${COLOR.grey[500]};
  }
`;

const colorObj = {
  primary,
  grey,
  black,
};

// Button Text Colors
const primaryText = `
  color: ${COLOR.primary[300]};
`;

const greyText = `
  color: ${COLOR.grey[400]};
`;

const warningText = `
  color: ${COLOR.error[300]};
`;

const textColorObj = {
  primary: primaryText,
  grey: greyText,
  warning: warningText,
};

// Button types
const text = `
  ${FONT_MIXIN.xSmall(500)}

  :hover {
    text-decoration: underline;
  }
`;

// Button Component
const Button = styled.button<ButtonStyleProps>`
  ${mixins.flexBox({})}
  ${({ size }) => size && sizeObj[size]};
  ${({ color }) => color && colorObj[color]};
  ${({ textColor }) => textColor && textColorObj[textColor]};
  ${({ isText }) => isText && text};
`;

export { Button };
