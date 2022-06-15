import styled from 'styled-components';

export interface ButtonStyleProps {
  size: string;
  isStandard: boolean;
}

const Button = styled.button<ButtonStyleProps>`
  width: 100px;
  height: 50px;
  background-color: ${({ theme: { color } }) => color.primary};
  color: ${({ theme: { color } }) => color.text};
  border-radius: 20px;
`;

export { Button };
