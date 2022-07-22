import * as S from '../style';
import { ColorChangeButtonProps } from '../type';

import * as I from '@/icons/Label';

const ColorChangeButton = ({ backgroundColor, onClick }: ColorChangeButtonProps) => {
  return (
    <S.ColorChangeButton backgroundColor={backgroundColor} type="button" onClick={onClick}>
      <I.Change />
    </S.ColorChangeButton>
  );
};

export default ColorChangeButton;
