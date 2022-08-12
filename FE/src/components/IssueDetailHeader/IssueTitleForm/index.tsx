import { useRef, useState } from 'react';

import * as S from './style';

import { patchIssue } from '@/apis/issueApi';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import { useRefetchIssueDetail } from '@/hooks/useIssue';

const IssueTitleForm = ({ id, title, onCancle }) => {
  const titleRef = useRef<HTMLInputElement>(null);
  const { mutate } = useRefetchIssueDetail(id);
  const [isButtonDisabled, setIsButtonDisabled] = useState(false);

  const handleSaveClick = () => {
    const issueData = {
      issueTitle: titleRef.current?.value,
    };
    patchIssue(id, issueData);
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
        defaultValue={title}
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
