import { createGlobalStyle, DefaultTheme } from 'styled-components';

import { font } from './font';

import fonts from '@/assets/fonts';
import Normalize from '@/styles/Normalize';

const GlobalStyle = createGlobalStyle<{ theme: DefaultTheme }>`
  ${Normalize}
  ${fonts}

  * {
    font-family: ${font.family};
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }

  body {
    color: ${({ theme }) => theme.color.text};
    background: ${({ theme }) => theme.color.bg};
    /* TODO: [Issue#17] transition: background 0.2s ease-in, color 0.2s ease-in; */
  }
  
  button,
  input,
  select,
  textarea {
    background-color: transparent;
    border: 0;
    &:focus {
      outline: none;
      box-shadow: none;
    }
  }

  a,
  button {
    text-decoration: none;
    color: inherit;
    cursor: pointer;
    :focus {
      outline: none;
    }
  }
  
  ul,
  li {
    padding: 0;
    list-style: none;
  }
`;

export default GlobalStyle;
