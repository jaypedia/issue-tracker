/* eslint-disable jsx-a11y/label-has-associated-control */
import * as S from './style';
import { InputProps } from './type';

const Input = ({
  inputStyle,
  title,
  placeholder,
  type,
  name,
  hasBorder,
  inputLabel,
  value,
}: InputProps) => {
  return (
    <S.InputLabel>
      {inputLabel}
      <S.Input
        type={type}
        title={title}
        placeholder={placeholder}
        inputStyle={inputStyle}
        hasBorder={hasBorder}
        name={name}
        value={value}
      />
    </S.InputLabel>
  );
};

export default Input;
