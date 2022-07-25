import styled from 'styled-components';

import { DetailsMenusStyle, IndicatorStyle } from '@/components/common/DropDown/type';
import { COLOR } from '@/styles/color';
import { FlexBetween } from '@/styles/common';
import { FONT_MIXIN, mixins } from '@/styles/mixins';

const DetailsMenuPositionObj = {
  left: 'left: 0;',
  right: 'right: 0;',
  center: 'left: 50%; trasnform: translateX(-50%);',
};

const DropDown = styled.details`
  position: relative;
`;

const beforeStyle = `
::before {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 80;
  display: block;
  cursor: default;
  content: ' ';
  background: transparent;
`;

const Indicator = styled.summary<IndicatorStyle>`
  ${mixins.flexBox({})}
  ${FONT_MIXIN.small(700)}
  gap: 4px;
  color: ${({ theme: { color } }) => color.dropDown.indicator};
  cursor: pointer;
  ${({ indicatorType }) => indicatorType === 'large' && `padding: 0 24px; gap: 16px; height: 38px;`}

  :hover {
    color: ${({ theme: { color } }) => color.dropDown.indicatorHover};
  }

  ::marker {
    font-size: 0;
  }

  ${({ hasBefore }) => hasBefore && beforeStyle}
`;

const DetailsMenu = styled.div<DetailsMenusStyle>`
  cursor: pointer;
  position: absolute;
  top: ${({ indicatorType }) =>
    indicatorType === 'setting' ? 'calc(100% - 10px)' : 'calc(100% + 8px)'};
  ${({ menuPosition }) => DetailsMenuPositionObj[menuPosition]};
  width: 300px;
  border-radius: ${({ indicatorType }) => (indicatorType === 'setting' ? '8px' : '16px')};
  border: 1px solid ${({ theme: { color } }) => color.line};
  overflow: hidden;
  z-index: 99;
`;

const DetailsMenuTitleWrapper = styled(FlexBetween)`
  cursor: default;
  padding: 8px 16px;
  background: ${({ theme: { color } }) => color.dropDown.bg.detailsMenuTitle};
`;

const DetailsMenuTitle = styled.p<IndicatorStyle>`
  ${({ indicatorType }) =>
    indicatorType === 'large' ? FONT_MIXIN.medium(400) : FONT_MIXIN.xSmall(400)};
`;

const DetailsMenuItem = styled.li<IndicatorStyle>`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
  border-top: 1px solid ${({ theme: { color } }) => color.line};
  ${FONT_MIXIN.small(400)}
  background: ${({ theme: { color }, indicatorType }) =>
    indicatorType === 'setting' ? color.inputBg : color.dropDown.bg.detailsMenuCell};
  color: ${({ theme: { color } }) => color.text};

  button {
    display: block;
    width: 100%;
    height: 100%;
    padding: 8px 16px;
    text-align: left;
  }

  input {
    display: none;
  }

  input[type='checkbox']:checked + label {
    background-color: ${COLOR.primary[100]};
  }

  input[type='radio']:checked + label {
    background-color: ${COLOR.primary[100]};
  }

  ${({ theme: { color }, indicatorType }) =>
    indicatorType &&
    `
   :hover {
    color: ${COLOR.white};
    background: ${color.primary[200]};
   }
  `}
`;

const CheckBox = styled.div`
  input[type='checkbox'] {
    display: none;
  }
  input[type='checkbox'] + label {
    display: block;
    font-size: 0;
    width: 16px;
    height: 16px;
    background-color: red;
    cursor: pointer;
  }

  input[type='checkbox']:checked + label {
    background-color: blue;
  }
`;

const TitleWrapper = styled.li`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
  width: 100%;
  padding: 20px 0;
  cursor: pointer;

  :hover > * {
    color: ${({ theme: { color } }) => color.primary[200]};
  }
`;

const Menu = styled.div`
  ${mixins.flexBox({ justifyContent: 'flex-start' })}
  ${FONT_MIXIN.small(400)}
  margin-left: 15px;

  & > :first-child {
    margin-right: 10px;
  }
`;

const CheckLabel = styled.label`
  ${mixins.flexBox({ justifyContent: 'flex-between' })}
  width: 100%;
  cursor: pointer;
`;

type LabelColorCircle = {
  backgroundColor: string;
};

const LabelColorCircle = styled.span<LabelColorCircle>`
  display: inline-block;
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background-color: ${({ backgroundColor }) => backgroundColor};
`;

const Title = styled.div``;

export {
  DropDown,
  Indicator,
  DetailsMenu,
  DetailsMenuTitle,
  DetailsMenuItem,
  DetailsMenuTitleWrapper,
  CheckBox,
  TitleWrapper,
  Menu,
  CheckLabel,
  LabelColorCircle,
  Title,
};
