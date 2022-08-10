import styled, { css } from 'styled-components';

import { FormStyleType, ColorChangeButtonProps } from './type';

import { FlexEnd } from '@/styles/common';
import { FONT_MIXIN } from '@/styles/mixins';

const createLabelStyle = css`
  background: ${({ theme: { color } }) => color.cell.bg.cellHeaderBg};
  border: 1px solid ${({ theme: { color } }) => color.line};
  padding: 20px;
`;

const LabelForm = styled.form<FormStyleType>`
  ${({ type }) => type === 'create' && createLabelStyle};
  border-radius: 10px;
`;

const GridContainer = styled.div`
  margin-top: 20px;
  display: grid;
  grid-template-columns: 1fr 1.5fr 1fr 1fr;

  & > *:not(:last-child) {
    margin-right: 15px;
  }
`;

const ButtonWrapper = styled(FlexEnd)`
  align-items: flex-end;

  & > :last-child {
    margin-left: 10px;
  }
`;

const ColorChangeButton = styled.button<ColorChangeButtonProps>`
  background-color: ${({ backgroundColor }) => backgroundColor};
  color: black; // Temp
  border-radius: 5px;
  padding: 5px;
  line-height: 0;
  margin-right: 7px;
`;

const InputLabel = styled.label`
  ${FONT_MIXIN.xSmall(700)}
`;

export { LabelForm, GridContainer, ButtonWrapper, ColorChangeButton, InputLabel };
