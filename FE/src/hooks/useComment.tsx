import { useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { editIssue, postComment } from '@/apis/issueApi';
import { useInput } from '@/hooks/useInput';
import { useRefetchIssueDetail } from '@/hooks/useIssue';
import { currentIssueState } from '@/stores/atoms/currentIssue';
import { userState } from '@/stores/atoms/user';

export type useCommentProps = {
  onCancel?: () => void;
  content?: string;
  isIssueContent: boolean;
  commentId?: number;
};

export const useComment = ({ onCancel, content, isIssueContent, commentId }: useCommentProps) => {
  const { id } = useParams();
  const userData = useRecoilValue(userState);
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const { mutate } = useRefetchIssueDetail(Number(id));
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const { value: commentValue, setValue: setCommentValue } = useInput(content || '');
  const currentIssueData = useRecoilValue(currentIssueState);

  const handleSubmit = () => {
    if (!commentRef.current) return;

    if (onCancel && isIssueContent) {
      const issueData = {
        ...currentIssueData,
        content: commentRef.current.value,
      };
      editIssue(Number(id), issueData);
      onCancel();
    } else {
      const commentData = {
        issueId: Number(id),
        content: commentRef.current.value,
      };
      postComment(commentData, commentId);
      setCommentValue('');
      setIsButtonDisabled(true);
    }
    mutate();
  };

  const handleTextAreaChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setCommentValue(e.target.value);
    if (commentRef.current?.value) {
      setIsButtonDisabled(false);
    } else {
      setIsButtonDisabled(true);
    }
  };

  return {
    handleSubmit,
    handleTextAreaChange,
    isButtonDisabled,
    commentValue,
    commentRef,
    userData,
  };
};
