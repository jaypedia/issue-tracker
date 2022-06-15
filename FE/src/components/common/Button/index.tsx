import * as S from './style';
import { ButtonStyleProps } from './style';

interface ButtonProps extends ButtonStyleProps {
  text: string;
}

const Button = ({ size, isStandard = true, text }: ButtonProps) => {
  return (
    <S.Button size={size} isStandard={isStandard}>
      {text}
    </S.Button>
  );
};

export default Button;
