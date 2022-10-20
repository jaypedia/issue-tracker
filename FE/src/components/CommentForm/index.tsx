import * as S from './style';

import Button from '@/components/common/Button';
import TextArea from '@/components/common/TextArea';
import UserProfile from '@/components/common/UserProfile';
import { COMMENT_FORM_TYPE } from '@/constants/constants';
import { useComment, useCommentProps } from '@/hooks/useComment';

export type CommentFormProps = useCommentProps & {
  usage: 'edit' | 'comment';
};

const CommentForm = ({ usage, onCancel, content, isIssueContent }: CommentFormProps) => {
  const {
    handleSubmit,
    handleTextAreaChange,
    isButtonDisabled,
    commentValue,
    commentRef,
    userData,
  } = useComment({ onCancel, content, isIssueContent });
  const buttonText = {
    edit: 'Update comment',
    comment: 'Comment',
  } as const;

  return (
    <S.CommentForm>
      <S.FlexWrapper>
        {usage !== COMMENT_FORM_TYPE.edit && (
          <UserProfile imgUrl={userData?.profileImageUrl} userId={userData?.name} size="large" />
        )}
        <S.CommentWrapper>
          <TextArea
            name="comment"
            usage="comment"
            ref={commentRef}
            defaultValue={content}
            onChange={handleTextAreaChange}
            value={commentValue}
          />
          <S.ButtonWrapper>
            {usage === COMMENT_FORM_TYPE.edit && (
              <Button size="small" color="grey" text="Cancel" type="button" onClick={onCancel} />
            )}
            <Button
              size="small"
              color="primary"
              text={buttonText[usage]}
              type="button"
              onClick={handleSubmit}
              disabled={usage === COMMENT_FORM_TYPE.comment && isButtonDisabled}
            />
          </S.ButtonWrapper>
        </S.CommentWrapper>
      </S.FlexWrapper>
    </S.CommentForm>
  );
};

export default CommentForm;
