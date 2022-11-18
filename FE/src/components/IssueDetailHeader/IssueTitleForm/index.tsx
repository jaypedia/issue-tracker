import { useRef, useState } from 'react';

import * as S from './style';

import { editIssue } from '@/apis/issueApi';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import { useRefetchIssueDetail } from '@/hooks/useIssue';
import { IssueType } from '@/types/issueTypes';

const IssueTitleForm = ({ data, onCancle }: { data: IssueType; onCancle: () => void }) => {
  const titleRef = useRef<HTMLInputElement>(null);
  const { mutate } = useRefetchIssueDetail(data.id);
  const [isButtonDisabled, setIsButtonDisabled] = useState(false);

  const handleSaveClick = () => {
    if (!titleRef.current) return;
    const issueData = {
      title: titleRef.current?.value,
      author: data.author,
      createdAt: data.createdAt,
      image: data.image,
      content: data.content,
    };
    editIssue(data.id, issueData);
    mutate();
    onCancle();
  };

  const handleInputChange = () => {
    if (titleRef.current?.value) {
      setIsButtonDisabled(false);
    } else {
      setIsButtonDisabled(true);
    }
  };

  return (
    <S.IssueTitleForm>
      <Input
        inputStyle="medium"
        hasBorder
        title="IssueTitle"
        type="text"
        name="Issue Title"
        defaultValue={data.title}
        ref={titleRef}
        onChange={handleInputChange}
      />
      <Button
        size="small"
        text="Save"
        color="grey"
        onClick={handleSaveClick}
        type="button"
        disabled={isButtonDisabled}
      />
      <Button size="small" text="Cancel" isText onClick={onCancle} type="button" />
    </S.IssueTitleForm>
  );
};

export default IssueTitleForm;
