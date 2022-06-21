import * as S from './style';

import { InputProps } from '@/type/input.type';

const Input = ({ inputStyle, title, placeholder, type, name }: InputProps) => {
  return (
    <S.Input
      type={type}
      title={title}
      placeholder={placeholder}
      inputStyle={inputStyle}
      name={name}
    />
  );
};

export default Input;
