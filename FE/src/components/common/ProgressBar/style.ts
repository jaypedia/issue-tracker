import { Link } from 'react-router-dom';
import styled, { css } from 'styled-components';

import { ProgressBarOptional } from './type';

import { COLOR } from '@/styles/color';
import { FlexBetween } from '@/styles/common';
import { FONT_MIXIN, mixins } from '@/styles/mixins';

const ProgressStyle = css<ProgressBarOptional>`
  height: ${({ size }) => (size === 'large' ? '12px' : '8px')};
  border-radius: 6px;
`;

const ProgressBack = styled.div`
  ${ProgressStyle}
  width: 100%;
  overflow: hidden;
  background-color: ${COLOR.transparent[100]};
  margin-bottom: 5px;
`;

const ProgressItem = styled.div`
  ${ProgressStyle}
  width: ${({ percent }) => `${percent}%`};
  background: linear-gradient(to right, ${COLOR.primary[100]}, ${COLOR.primary[200]});
`;

const MilestoneLink = styled(Link)`
  ${FONT_MIXIN.xSmall(500)}
  margin-left: 10px;

  :hover {
    color: ${COLOR.primary[300]};
  }
`;

// TODO: 추후 open, close 버튼 클릭 시 Filtering 기능 추가, font-size 추가
const MilestoneStats = styled(FlexBetween)`
  font-size: 14px;
`;

const ProgressBarWrapper = styled.div<ProgressBarOptional>`
  ${({ size }) => size === 'large' && 'width: 100%'};
  ${mixins.flexBox({ direction: 'column', alignItems: 'flex-start' })};
`;

export { ProgressBack, ProgressItem, MilestoneLink, MilestoneStats, ProgressBarWrapper };
