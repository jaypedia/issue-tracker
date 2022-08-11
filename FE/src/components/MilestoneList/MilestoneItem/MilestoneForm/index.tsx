import { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import * as S from './style';

import { postMilestone, editMilestone } from '@/apis/milestoneApi';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import TextArea from '@/components/common/TextArea';
import { FORM_TYPE } from '@/constants/constants';
import { useRefetchMilestone } from '@/hooks/useMilestone';
import { MilestoneType } from '@/types/milestoneTypes';

type MilestoneFormProps = {
  data?: MilestoneType;
  type: 'edit' | 'create';
  onCancel?: () => void;
};

const MilestoneForm = ({ data, type, onCancel }: MilestoneFormProps) => {
  const navigate = useNavigate();
  const titleRef = useRef<HTMLInputElement>(null);
  const descriptionRef = useRef<HTMLTextAreaElement>(null);
  const dueDateRef = useRef<HTMLInputElement>(null);
  const { mutate } = useRefetchMilestone();
  const [isButtonDisabled, setIsButtonDisabled] = useState(type === FORM_TYPE.create);

  const handleCancelClick = () => {
    if (type === FORM_TYPE.create) {
      navigate('/milestones');
    } else if (onCancel) {
      onCancel();
    }
  };

  const handleInputChange = () => {
    if (!titleRef.current) return;

    if (titleRef.current.value) {
      setIsButtonDisabled(false);
    } else {
      setIsButtonDisabled(true);
    }
  };

  const saveMilestone = () => {
    if (!titleRef.current || !descriptionRef.current || !dueDateRef.current) return;

    const milestoneData = {
      title: titleRef.current.value,
      dueDate: dueDateRef.current.value,
      updatedAt: new Date().toString(),
      description: descriptionRef.current.value,
    };

    if (type === FORM_TYPE.create) {
      postMilestone(milestoneData);
      mutate();
      navigate('/milestones');
      return;
    }

    if (type === FORM_TYPE.edit && data && onCancel) {
      editMilestone(data.id, milestoneData);
      onCancel();
      mutate();
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
        defaultValue={data?.title}
        ref={titleRef}
        onChange={handleInputChange}
      />
      <Input
        name="dueDate"
        placeholder="yyyy-mm-dd"
        inputStyle="medium"
        title="DueDate"
        type="text"
        inputLabel="Due date (optional)"
        maxLength={10}
        defaultValue={data?.dueDate}
        ref={dueDateRef}
      />
      <TextArea
        name="description"
        usage="milestone"
        textareaLabel="Description"
        defaultValue={data?.description}
        ref={descriptionRef}
      />
      <S.ButtonWrapper>
        <Button size="small" color="grey" text="Cancel" type="button" onClick={handleCancelClick} />
        <Button
          size="small"
          color="primary"
          text={type === FORM_TYPE.create ? 'Create milestone' : 'Save changes'}
          type="button"
          onClick={saveMilestone}
          disabled={isButtonDisabled}
        />
      </S.ButtonWrapper>
    </S.MilestoneForm>
  );
};

export default MilestoneForm;
