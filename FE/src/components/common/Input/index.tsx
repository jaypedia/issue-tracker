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
  defaultValue,
  value,
  onChange,
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
        defaultValue={defaultValue}
        value={value}
        onChange={onChange}
      />
    </S.InputLabel>
  );
};

export default Input;
