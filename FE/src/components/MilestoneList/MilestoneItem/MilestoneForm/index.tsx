import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';

import * as S from './style';

import { postMilestone, patchMilestone } from '@/apis/milestoneApi';
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
  const { mutate } = useRefetchMilestone();

  const handleCancelClick = () => {
    if (type === FORM_TYPE.create) {
      navigate('/milestones');
    } else if (onCancel) {
      onCancel();
    }
  };

  const saveMilestone = () => {
    if (!titleRef.current || !descriptionRef.current) return;

    const milestoneData = {
      title: titleRef.current.value,
      dueDate: new Date().toString(),
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
      patchMilestone(data.id, milestoneData);
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
        />
      </S.ButtonWrapper>
    </S.MilestoneForm>
  );
};

export default MilestoneForm;
