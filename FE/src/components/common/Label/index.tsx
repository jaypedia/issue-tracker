import * as S from './style';
import { LabelProps } from './type';

const Label = ({ size, title, backgroundColor, textColor, hasLine }: LabelProps) => {
  return (
    <S.Label size={size} backgroundColor={backgroundColor} textColor={textColor} hasLine={hasLine}>
      {title}
    </S.Label>
  );
};

export default Label;
