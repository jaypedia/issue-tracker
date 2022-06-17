import * as S from './style';
import { InputStyleProps } from './style';

type InputProps = InputStyleProps & {
  title: string;
  placeholder: string;
  type: string;
};

const Input = ({ inputStyle, title, placeholder, type }: InputProps) => {
  return <S.Input type={type} title={title} placeholder={placeholder} inputStyle={inputStyle} />;
};

export default Input;
