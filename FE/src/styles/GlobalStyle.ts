import { createGlobalStyle, DefaultTheme } from 'styled-components';

import Normalize from '@/styles/Normalize';

const GlobalStyle = createGlobalStyle<{ theme: DefaultTheme }>`
  ${Normalize}

  * {
    font-family: ${({ theme }) => theme.font.family};
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }

  body {
    color: ${({ theme }) => theme.color.text};
    background: ${({ theme }) => theme.color.bg};
    transition: background 0.2s ease-in, color 0.2s ease-in;
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
  }
  
  ul,
  li {
    padding: 0;
    list-style: none;
  }
`;

export default GlobalStyle;
