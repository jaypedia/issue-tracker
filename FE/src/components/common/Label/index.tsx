import * as S from './style';
import { LabelProps } from './type';

import { FlexBetween } from '@/styles/common';

const Label = ({ size, title, backgroundColor, textColor, hasLine, icon }: LabelProps) => {
  return (
    <S.Label size={size} backgroundColor={backgroundColor} textColor={textColor} hasLine={hasLine}>
      <FlexBetween>
        {icon}
        <S.LabelName>{title}</S.LabelName>
      </FlexBetween>
    </S.Label>
  );
};

export default Label;
