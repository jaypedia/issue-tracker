import styled from 'styled-components';

import { DetailsMenusStyle, IndicatorStyle } from '@/components/common/DropDown/type';
import { COLOR } from '@/styles/color';
import { FONT_MIXIN, mixins } from '@/styles/mixins';

const DetailsMenuPositionObj = {
  left: 'left: 0;',
  right: 'right: 0;',
  center: 'left: 50%; trasnform: translateX(-50%);',
};

const DropDown = styled.details`
  position: relative;
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
  z-index: 10;
`;

const DetailsMenuTitle = styled.p<IndicatorStyle>`
  padding: 8px 16px;
  ${({ indicatorType }) =>
    indicatorType === 'large' ? FONT_MIXIN.medium(400) : FONT_MIXIN.xSmall(400)};
  background: ${({ theme: { color } }) => color.dropDown.bg.detailsMenuTitle};
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
    background-color: blue;
  }

  input[type='radio']:checked + label {
    background-color: blue;
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

const CheckBoxIcon = styled.div`
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

const Title = styled.div``;

const CheckLabel = styled.label`
  ${mixins.flexBox({ justifyContent: 'flex-start' })}
  width: 100%;
  cursor: pointer;
`;

export {
  DropDown,
  Indicator,
  DetailsMenu,
  DetailsMenuTitle,
  DetailsMenuItem,
  CheckBoxIcon,
  TitleWrapper,
  Title,
  CheckLabel,
};
