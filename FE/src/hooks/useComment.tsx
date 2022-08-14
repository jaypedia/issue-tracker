import { useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { patchIssue, postComment } from '@/apis/issueApi';
import { CommentFormProps } from '@/components/CommentForm';
import { COMMENT_FORM_TYPE, USER } from '@/constants/constants';
import { useInput } from '@/hooks/useInput';
import { useRefetchIssueDetail } from '@/hooks/useIssue';
import { userState } from '@/stores/atoms/user';

export const useComment = ({
  usage,
  onCancel,
  content,
  author = '',
  image = '',
}: CommentFormProps) => {
  const { id } = useParams();
  const userData = useRecoilValue(userState);
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const { mutate } = useRefetchIssueDetail(Number(id));
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const { value: commentValue, setValue: setCommentValue } = useInput(content || '');

  const handleSubmit = () => {
    if (!commentRef.current) return;
    if (usage === COMMENT_FORM_TYPE.edit && onCancel) {
      const issueData = {
        author,
        image,
        content: commentRef.current.value,
        createdAt: new Date().toString(),
      };

      patchIssue(Number(id), issueData);
      onCancel();
      mutate();
      return;
    }

    if (usage === COMMENT_FORM_TYPE.comment) {
      const commentData = {
        author: userData?.name || USER.name,
        image: userData?.profileImageUrl || USER.image,
        content: commentRef.current.value,
        createdAt: new Date().toString(),
      };

      postComment(Number(id), commentData);
      setCommentValue('');
      setIsButtonDisabled(true);
      mutate();
    }
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
