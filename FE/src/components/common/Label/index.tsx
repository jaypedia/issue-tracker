import * as S from './style';
import { LabelProps } from './type';

const Label = ({ size, title, backgroundColor, textColor }: LabelProps) => {
  return (
    <S.Label size={size} backgroundColor={backgroundColor} textColor={textColor}>
      {title}
    </S.Label>
  );
};

export default Label;
