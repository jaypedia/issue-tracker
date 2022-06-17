import * as S from './style';
import { ButtonStyleProps } from './style';

type ButtonProps = ButtonStyleProps & {
  text: string;
  type?: 'button' | 'submit' | 'reset' | undefined;
};

const Button = ({ btnSize, btnColor, text, type }: ButtonProps) => {
  return (
    <S.Button btnSize={btnSize} btnColor={btnColor} type={type}>
      {text}
    </S.Button>
  );
};

export default Button;
