import * as S from './style';

import { ButtonProps } from '@/type/button.type';

const Button = ({ btnSize, btnColor, text, type, changeTag }: ButtonProps) => {
  return (
    <S.Button btnSize={btnSize} btnColor={btnColor} type={type} as={changeTag}>
      {text}
    </S.Button>
  );
};

export default Button;
