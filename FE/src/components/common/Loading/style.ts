import styled, { keyframes } from 'styled-components';

import { COLOR } from '@/styles/color';
import { FONT_MIXIN, mixins } from '@/styles/mixins';

const LoadingContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  ${mixins.flexBox({})}
  gap: 3px;
`;

const LoadingAnimation = keyframes`
  0% { transform: translate(0px, 0px); }
  25% { transform: translate(0px, -10px); }
  100% { transform: translate(0px, 0px); }
`;

const Text = styled.span`
  animation-name: ${LoadingAnimation};
  animation-duration: 1s;
  animation-iteration-count: infinite;
  animation-timing-function: linear;
  ${FONT_MIXIN.large(700)}
  color: ${COLOR.primary[300]};

  &:nth-child(1) {
    animation-delay: 0.1s;
  }
  &:nth-child(2) {
    animation-delay: 0.2s;
  }
  &:nth-child(3) {
    animation-delay: 0.3s;
  }
  &:nth-child(4) {
    animation-delay: 0.4s;
  }
  &:nth-child(5) {
    animation-delay: 0.5s;
  }
  &:nth-child(6) {
    animation-delay: 0.6s;
  }
  &:nth-child(7) {
    animation-delay: 0.7s;
  }
`;

export { LoadingContainer, Text };
