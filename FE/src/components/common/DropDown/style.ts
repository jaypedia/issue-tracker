import styled from 'styled-components';

import { FONT_MIXIN, mixins } from '@/styles/mixins';
import { DetailsMenusSytle, IndicatorsSytle } from '@/type/dropDown.type';

const DetailsMenuPositionObj = {
  left: 'left: 0;',
  right: 'right: 0;',
  center: 'left: 50%; trasnform: translateX(-50%);',
};

const DropDown = styled.details`
  position: relative;
`;

const Indicators = styled.summary<IndicatorsSytle>`
  ${mixins.flexBox({})}
  ${FONT_MIXIN.small(700)}
  gap: 4px;
  color: ${({ theme: { color } }) => color.dropDown.indicators};
  cursor: pointer;
  ${({ IndicatorsSize }) =>
    IndicatorsSize === 'large' && `padding: 0 24px; gap: 16px; height: 38px;`}

  :hover {
    color: ${({ theme: { color } }) => color.dropDown.indicatorsHover};
  }

  ::marker {
    font-size: 0;
  }
`;

const DetailsMenu = styled.div<DetailsMenusSytle>`
  position: absolute;
  top: calc(100% + 8px);
  ${({ menuPosition }) => DetailsMenuPositionObj[menuPosition]}
  width: 300px;
  border-radius: 16px;
  border: 1px solid ${({ theme: { color } }) => color.line};
  overflow: hidden;
`;

const DetailsMenuTitle = styled.p`
  padding: 8px 16px;
  ${FONT_MIXIN.medium(400)};
  background: ${({ theme: { color } }) => color.dropDown.bg.detailsMenuTitle};
`;

const DetailsMenuItem = styled.li`
  ${mixins.flexBox({ justifyContent: 'space-between' })}
  border-top: 1px solid ${({ theme: { color } }) => color.line};
  ${FONT_MIXIN.small(400)}
  background: ${({ theme: { color } }) => color.dropDown.bg.detailsMenuCell};

  button {
    display: block;
    width: 100%;
    height: 100%;
    padding: 8px 16px;
    text-align: left;
  }
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

export { DropDown, Indicators, DetailsMenu, DetailsMenuTitle, DetailsMenuItem, CheckBoxIcon };
