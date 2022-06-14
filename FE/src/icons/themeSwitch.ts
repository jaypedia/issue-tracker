import { BsMoonStarsFill } from 'react-icons/bs';
import { IoSunny } from 'react-icons/io5';
import styled from 'styled-components';

const themeSwitchStyle = `
display: block;
width: 100%;
height: 100%;
padding: 10px;
transition: 0.2s ease-in;
`;

const Sun = styled(IoSunny)`
  ${themeSwitchStyle}

  :hover {
    fill: #5138ee;
  }
`;

const Moon = styled(BsMoonStarsFill)`
  ${themeSwitchStyle}
  fill: white;

  :hover {
    fill: #c9c0fa;
  }
`;

export { Sun, Moon };
