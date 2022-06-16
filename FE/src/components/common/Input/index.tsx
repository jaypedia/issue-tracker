import * as S from './style';
import { InputStyleProps } from './style';

type InputProps = InputStyleProps & {
  title: string;
  placeholder: string;
};

const Input = ({ inputStyle, title, placeholder }: InputProps) => {
  return <S.Input title={title} placeholder={placeholder} inputStyle={inputStyle} />;
};

export default Input;
