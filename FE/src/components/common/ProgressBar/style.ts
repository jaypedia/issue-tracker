import { Link } from 'react-router-dom';
import styled, { css } from 'styled-components';

import { ProgressBarProps } from './type';

import { COLOR } from '@/styles/color';
import { FONT_MIXIN, mixins } from '@/styles/mixins';

const ProgressStyle = css<ProgressBarProps>`
  height: ${({ size }) => (size === 'large' ? '12px' : '8px')};
  border-radius: 6px;
`;

const ProgressBack = styled.div`
  ${ProgressStyle}
  width: 100%;
  overflow: hidden;
  background-color: ${COLOR.transparent[100]};
`;

const ProgressItem = styled.div`
  ${ProgressStyle}
  width: 50%;
  background: linear-gradient(to right, ${COLOR.primary[100]}, ${COLOR.primary[200]});
`;

const MilestoneLink = styled(Link)`
  ${FONT_MIXIN.xSmall(700)}
  margin-top: 10px;
`;

// TODO: 추후 open, close 버튼 클릭 시 Filtering 기능 추가, font-size 추가
const MilestoneStats = styled.div`
  font-size: 14px;
  margin-top: 8px;
`;

const ProgressBarWrapper = styled.div<ProgressBarProps>`
  ${({ size }) => size === 'large' && 'width: 60%'};
  ${mixins.flexBox({ direction: 'column', alignItems: 'flex-start' })};
`;

export { ProgressBack, ProgressItem, MilestoneLink, MilestoneStats, ProgressBarWrapper };
