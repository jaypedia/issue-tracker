import styled from 'styled-components';

import { LabelStyle } from './type';

import { COLOR } from '@/styles/color';
import { FONT_MIXIN } from '@/styles/mixins';

const LargeStyle = `
  padding: 5px 12px;
  text-align: center;
  color: ${COLOR.white};
  ${FONT_MIXIN.small(500)}

  & > :first-child {
    margin-right: 5px;
  }
`;

const SmallStyle = `
  padding: 4px 16px;
  ${FONT_MIXIN.xSmall(700)}
`;

const LineStyle = `
  padding: 2px 16px;
  border-width: 1px;
  border-style: solid;
  ${FONT_MIXIN.xSmall(700)}
`;

const LabelStyleObj = {
  large: LargeStyle,
  small: SmallStyle,
  line: LineStyle,
};

const Label = styled.div<LabelStyle>`
  color: ${({ theme, textColor, hasLine }) => (hasLine ? theme.color.text : textColor)};
  border-radius: 30px;
  background: ${({ backgroundColor }) => backgroundColor};
  border-color: ${({ theme: { color } }) => color.line};
  ${({ size }) => LabelStyleObj[size]};
  ${({ hasLine }) => hasLine && LabelStyleObj.line};
`;

const LabelName = styled.span``;

export { Label, LabelName };
