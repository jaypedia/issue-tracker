import * as S from './style';

import { deleteComment } from '@/apis/issueApi';
import CommentForm from '@/components/CommentForm';
import Label from '@/components/common/Label';
import UserProfile from '@/components/common/UserProfile';
import useBoolean from '@/hooks/useBoolean';
import { useRefetchIssueDetail } from '@/hooks/useIssue';
import { Edit } from '@/icons/Edit';
import { Trash } from '@/icons/Trash';
import { getRelativeTime } from '@/utils/issue';

export type CommentProps = {
  issueId: number;
  commentId?: number;
  issueAuthor: string | undefined;
  imgUrl: string;
  userId: string;
  createdAt: string;
  description: string;
  isIssueContent: boolean;
};

const Comment = ({
  issueId,
  commentId,
  issueAuthor,
  userId,
  imgUrl,
  createdAt,
  description,
  isIssueContent,
}: CommentProps) => {
  const timeCalc = getRelativeTime(createdAt);
  const { booleanState: isFormOpen, setTrue, setFalse } = useBoolean(false);
  const { mutate } = useRefetchIssueDetail(issueId);

  const handleEditButtonClick = () => {
    setTrue();
  };

  const handleDeleteButtonClick = () => {
    if (!commentId) return;
    deleteComment(commentId);
    mutate();
  };

  return (
    <S.CommentWrapper>
      <UserProfile size="large" imgUrl={imgUrl} userId={userId} />
      {isFormOpen ? (
        <CommentForm
          usage="edit"
          onCancel={setFalse}
          content={description}
          isIssueContent={isIssueContent}
        />
      ) : (
        <S.CommentContainer>
          <S.CommentHeader>
            <S.Flex>
              <S.UserId>{userId}</S.UserId>
              <S.Time>commented {timeCalc}</S.Time>
            </S.Flex>
            <S.Flex>
              {issueAuthor && <Label size="small" title="Author" backgroundColor="none" hasLine />}
              <S.EditButton type="button" onClick={handleEditButtonClick}>
                <Edit />
              </S.EditButton>
              {isIssueContent || (
                <S.DeleteButton type="button" onClick={handleDeleteButtonClick}>
                  <Trash />
                </S.DeleteButton>
              )}
            </S.Flex>
          </S.CommentHeader>
          <S.CommentContents>{description || 'No description provided.'}</S.CommentContents>
        </S.CommentContainer>
      )}
    </S.CommentWrapper>
  );
};

export default Comment;
