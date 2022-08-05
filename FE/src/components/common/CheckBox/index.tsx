import * as S from './style';

type CheckBoxProps = {
  id: string;
  isHeader?: boolean;
  onChange?: React.ChangeEventHandler<HTMLInputElement>;
};

const CheckBox = ({ id, isHeader, onChange }: CheckBoxProps) => {
  return (
    <S.CheckBox>
      <S.Label htmlFor={id} isHeader={isHeader}>
        <input type="checkbox" id={id} onChange={onChange} />
      </S.Label>
    </S.CheckBox>
  );
};

export default CheckBox;
