import * as S from './style';

const CheckBoxIcon = () => {
  return (
    <S.CheckBoxIcon>
      <label htmlFor="allIssues">
        <input type="checkbox" id="allIssues" />
      </label>
    </S.CheckBoxIcon>
  );
};

export default CheckBoxIcon;
