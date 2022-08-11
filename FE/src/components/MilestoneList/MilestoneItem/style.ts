import styled from 'styled-components';

import { Flex } from '@/styles/common';
import { FONT_MIXIN, mixins } from '@/styles/mixins';

const MilestoneInfoBox = styled.div`
  ${mixins.flexBox({ direction: 'column', alignItems: 'flex-start' })}
  width: 650px;

  & > * {
    margin-bottom: 10px;
  }

  & > :last-child {
    margin-top: 10px;
  }

  & > :not(:first-child) {
    color: ${({ theme: { color } }) => color.lightText};
  }
`;

const MilestoneTitle = styled.h2``;

const MilestoneMetaBox = styled(Flex)`
  ${FONT_MIXIN.xSmall(400)}

  & > * {
    margin-right: 10px;
  }
`;

const MilestoneMetaItem = styled(Flex)`
  & > * {
    margin-right: 5px;
  }
`;

type MetaSentenceType = {
  type?: 'warning';
};

const MetaSentence = styled.p<MetaSentenceType>`
  font-size: 14px;
  ${({ type }) => type === 'warning' && 'font-weight: bold'};
`;

const ProgressBarBox = styled.div`
  width: 100%;
  ${mixins.flexBox({ direction: 'column', alignItems: 'flex-start' })}
  margin-top: 8px;

  & > :last-child {
    margin-top: 10px;
  }
`;

const ButtonBox = styled(Flex)`
  & > * {
    margin-right: 7px;
  }
`;

export {
  MilestoneInfoBox,
  MilestoneTitle,
  MilestoneMetaBox,
  MilestoneMetaItem,
  ProgressBarBox,
  ButtonBox,
  MetaSentence,
};
