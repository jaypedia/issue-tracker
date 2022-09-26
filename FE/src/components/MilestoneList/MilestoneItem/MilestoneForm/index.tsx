import { useRef, useState } from 'react';

import * as S from './style';

import { postMilestone, editMilestone } from '@/apis/milestoneApi';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import TextArea from '@/components/common/TextArea';
import { FORM_TYPE } from '@/constants/constants';
import { useRefetchMilestone } from '@/hooks/useMilestone';
import useMovePage from '@/hooks/useMovePage';
import { IMilestone } from '@/types/milestoneTypes';

type MilestoneFormProps = {
  data?: IMilestone;
  type: 'edit' | 'create';
  onCancel?: () => void;
};

const MilestoneForm = ({ data, type, onCancel }: MilestoneFormProps) => {
  const [goMilestone] = useMovePage('/milestones');
  const titleRef = useRef<HTMLInputElement>(null);
  const descriptionRef = useRef<HTMLTextAreaElement>(null);
  const dueDateRef = useRef<HTMLInputElement>(null);
  const { mutate } = useRefetchMilestone();
  const [isButtonDisabled, setIsButtonDisabled] = useState(type === FORM_TYPE.create);

  const handleCancelClick = () => {
    if (type === FORM_TYPE.create) {
      goMilestone();
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
      goMilestone();
      return;
    }

    if (type === FORM_TYPE.edit && data && onCancel) {
      editMilestone(data.id, milestoneData);
      onCancel();
      mutate();
    }
  };

  const getDueDateString = (dueDate: string | undefined) => {
    if (!dueDate) return '';
    return dueDate.slice(0, 10);
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
        type="date"
        pattern="\d\d\d\d-\d\d-\d\d"
        placeholder="yyyy-mm-dd"
        name="dueDate"
        inputStyle="medium"
        title="DueDate"
        inputLabel="Due date (optional)"
        defaultValue={getDueDateString(data?.dueDate)}
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
