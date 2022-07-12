import * as S from './style';
import { ButtonProps } from './type';

const Button = ({ btnStyle, textColor, btnColor, type, changeTag, onClick, text }: ButtonProps) => {
  return (
    <S.Button
      btnStyle={btnStyle}
      textColor={textColor}
      btnColor={btnColor}
      type={type}
      as={changeTag}
      onClick={onClick}
    >
      {text}
    </S.Button>
  );
};

export default Button;
