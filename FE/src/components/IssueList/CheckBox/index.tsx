import * as S from './style';

type CheckBoxProps = {
  id: string;
  isHeader?: boolean;
};

const CheckBox = ({ id, isHeader }: CheckBoxProps) => {
  return (
    <S.CheckBox>
      <S.Label htmlFor={id} isHeader={isHeader}>
        <input type="checkbox" id={id} />
      </S.Label>
    </S.CheckBox>
  );
};

export default CheckBox;
