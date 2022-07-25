import { GoTag } from 'react-icons/go';
import { TiArrowSync } from 'react-icons/ti';
import styled from 'styled-components';

type Change = {
  iconColor: string | undefined;
};

export const Label = styled(GoTag)`
  width: 16px;
  height: 16px;
`;

export const Change = styled(TiArrowSync)<Change>`
  width: 20px;
  height: 20px;
  color: ${({ iconColor }) => iconColor};
`;
