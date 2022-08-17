import * as S from './style';

type CheckBoxProps = {
  id: string;
  isHeader?: boolean;
  onChange?: React.ChangeEventHandler<HTMLInputElement>;
  checked?: boolean;
};

const CheckBox = ({ id, isHeader, onChange, checked }: CheckBoxProps) => {
  return (
    <S.CheckBox>
      <S.Label htmlFor={id} isHeader={isHeader}>
        <input type="checkbox" id={id} onChange={onChange} checked={checked} />
      </S.Label>
    </S.CheckBox>
  );
};

export default CheckBox;
