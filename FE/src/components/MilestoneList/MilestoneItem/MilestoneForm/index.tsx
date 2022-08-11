import { useNavigate } from 'react-router-dom';

import * as S from './style';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import TextArea from '@/components/common/TextArea';
import { FORM_TYPE } from '@/constants/constants';

const MilestoneForm = ({ type, onCancel }) => {
  const navigate = useNavigate();

  const handleCancelClick = () => {
    if (type === FORM_TYPE.create) {
      navigate('/milestones');
    } else {
      onCancel();
    }
  };

  return (
    <S.MilestoneForm>
      <Input
        name="title"
        placeholder="Title"
        inputStyle="medium"
        title="Title"
        type="text"
        inputLabel="Title"
        maxLength={50}
      />
      <TextArea name="description" usage="milestone" textareaLabel="Description" />
      <S.ButtonWrapper>
        <Button size="small" color="grey" text="Cancel" type="button" onClick={handleCancelClick} />
        <Button
          size="small"
          color="primary"
          text={type === FORM_TYPE.create ? 'Create milestone' : 'Save changes'}
          type="submit"
        />
      </S.ButtonWrapper>
    </S.MilestoneForm>
  );
};

export default MilestoneForm;
