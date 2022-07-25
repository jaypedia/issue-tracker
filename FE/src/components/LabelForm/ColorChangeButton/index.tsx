import * as S from '../style';
import { ColorChangeButtonProps } from '../type';

import * as I from '@/icons/Label';

const ColorChangeButton = ({ backgroundColor, onClick, iconColor }: ColorChangeButtonProps) => {
  return (
    <S.ColorChangeButton backgroundColor={backgroundColor} type="button" onClick={onClick}>
      <I.Change iconColor={iconColor} />
    </S.ColorChangeButton>
  );
};

export default ColorChangeButton;
