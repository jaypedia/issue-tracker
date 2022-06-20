import styled from 'styled-components';

import { COLOR } from '@/styles/color';
import { FONT_MIXIN } from '@/styles/mixins';

export type ButtonStyleProps = {
  btnSize: 'large' | 'medium' | 'small';
  btnColor: 'primary' | 'grey' | 'black';
};

const PrimaryStyle = `
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

const GreyStyle = `
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

const blackStyle = `
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

const btnColorObj = {
  primary: PrimaryStyle,
  grey: GreyStyle,
  black: blackStyle,
};

const largeStyle = `
  width: 340px;
  height: 64px;
  border-radius: 20px;
  ${FONT_MIXIN.medium(700)}
`;

const mediumStyle = `
  width: 240px;
  height: 56px;
  border-radius: 20px;
  ${FONT_MIXIN.medium(700)}
`;

const smallStyle = `
  width: 120px;
  height: 40px;
  border-radius: 11px;
  ${FONT_MIXIN.xSmall(700)}
`;

const btnSizeObj = {
  large: largeStyle,
  medium: mediumStyle,
  small: smallStyle,
};

const Button = styled.button<ButtonStyleProps>`
  ${({ btnColor }) => btnColorObj[btnColor]}
  ${({ btnSize }) => btnSizeObj[btnSize]}
`;

export { Button };
