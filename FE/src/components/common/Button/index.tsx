import * as S from './style';
import { ButtonStyleProps } from './style';

type ButtonProps = ButtonStyleProps & {
  text: string;
};

const Button = ({ btnSize, btnColor, text }: ButtonProps) => {
  return (
    <S.Button btnSize={btnSize} btnColor={btnColor}>
      {text}
    </S.Button>
  );
};

export default Button;
