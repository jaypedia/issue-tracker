import styled from 'styled-components';

import { headerWrapperStyle } from '@/styles/common';
import { mixins } from '@/styles/mixins';

const NewIssueHeaderWrapper = styled.div`
  ${mixins.flexBox({ justifyContent: 'flex-start' })}
  ${headerWrapperStyle}
`;

export { headerWrapperStyle, NewIssueHeaderWrapper };
