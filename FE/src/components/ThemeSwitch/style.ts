import styled from 'styled-components';

const ThemeSwitch = styled.button`
  position: fixed;
  right: 30px;
  bottom: 30px;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: #2a2748;
  box-shadow: 0px 0px 5px rgb(0 0 0 / 30%);
  transition: 0.2s ease-in;

  :hover {
    background: ${({ theme }) => theme.color.text};
  }
`;

export { ThemeSwitch };
