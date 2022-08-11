import styled from 'styled-components';

import { headerWrapperStyle } from '@/styles/common';
import { mixins } from '@/styles/mixins';

const NewMilestoneHeaderWrapper = styled.div`
  ${mixins.flexBox({ justifyContent: 'flex-start', direction: 'column', alignItems: 'flex-start' })}
  ${headerWrapperStyle}
`;

export { NewMilestoneHeaderWrapper };
