import styled from 'styled-components';

import { LabelStyle } from './type';

import { COLOR } from '@/styles/color';
import { FONT_MIXIN } from '@/styles/mixins';

const LargeStyle = `
  width: 100px;
  padding: 10px 16px;
  text-align: center;
  color: ${COLOR.white};
`;
const SmallStyle = `
  padding: 4px 16px;
`;

const LabelStyleObj = {
  large: LargeStyle,
  small: SmallStyle,
};

const Label = styled.div<LabelStyle>`
  ${FONT_MIXIN.xSmall(700)}
  color: ${({ textColor }) => textColor};
  border-radius: 30px;
  background: ${({ backgroundColor }) => backgroundColor};
  ${({ size }) => LabelStyleObj[size]}
`;

export { Label };
