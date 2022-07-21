import { AiOutlineClockCircle, AiOutlineWarning } from 'react-icons/ai';
import { BsCalendarCheck } from 'react-icons/bs';
import { VscMilestone } from 'react-icons/vsc';
import styled from 'styled-components';

const iconSize16 = `
  width: 16px;
  height: 16px; 
`;

export const Milestone = styled(VscMilestone)`
  ${iconSize16}
`;

export const Clock = styled(AiOutlineClockCircle)`
  ${iconSize16}
`;

export const Calendar = styled(BsCalendarCheck)`
  ${iconSize16}
`;

export const Warning = styled(AiOutlineWarning)`
  width: 20px;
  height: 20px;
`;
