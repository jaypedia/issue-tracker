import { BiTrash } from 'react-icons/bi';
import styled from 'styled-components';

import { COLOR } from '@/styles/color';

export const Trash = styled(BiTrash)`
  width: 16px;
  height: 16px;

  :hover {
    color: ${COLOR.error[200]};
  }
`;
