import * as S from './style';
import { ButtonProps } from './type';

const Button = ({
  size,
  textColor,
  color,
  type,
  isText,
  changeTag,
  onClick,
  text,
  disabled,
}: ButtonProps) => {
  return (
    <S.Button
      size={size}
      textColor={textColor}
      color={color}
      type={type}
      as={changeTag}
      isText={isText}
      onClick={onClick}
      disabled={disabled}
    >
      {text}
    </S.Button>
  );
};

export default Button;
