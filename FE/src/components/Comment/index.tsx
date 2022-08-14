import * as S from './style';

import CommentForm from '@/components/CommentForm';
import Label from '@/components/common/Label';
import UserProfile from '@/components/common/UserProfile';
import useBoolean from '@/hooks/useBoolean';
import { Emoji } from '@/icons/Emoji';
import { getRelativeTime } from '@/utils/issue';

export type CommentProps = {
  issueAuthor: string | undefined;
  imgUrl: string;
  userId: string;
  createdAt: string;
  description: string;
};

const Comment = ({ issueAuthor, userId, imgUrl, createdAt, description }: CommentProps) => {
  const timeCalc = getRelativeTime(createdAt);
  const { booleanState: isFormOpen, setTrue, setFalse } = useBoolean(false);

  const handleEditClick = () => {
    setTrue();
  };

  return (
    <S.CommentWrapper>
      <UserProfile size="large" imgUrl={imgUrl} userId={userId} />
      {isFormOpen ? (
        <CommentForm
          usage="edit"
          onCancel={setFalse}
          author={issueAuthor}
          image={imgUrl}
          content={description}
        />
      ) : (
        <S.CommentContainer>
          <S.CommentHeader>
            <S.Flex>
              <S.UserId>{userId}</S.UserId>
              <S.Time>commented {timeCalc}</S.Time>
            </S.Flex>
            <S.Flex>
              {issueAuthor && (
                <>
                  <Label size="small" title="Author" backgroundColor="none" hasLine />
                  <S.EditButton type="button" onClick={handleEditClick}>
                    edit
                  </S.EditButton>
                </>
              )}
              <S.EmojiButton type="button">
                <Emoji />
              </S.EmojiButton>
            </S.Flex>
          </S.CommentHeader>
          <S.CommentContents>{description || 'No description provided.'}</S.CommentContents>
        </S.CommentContainer>
      )}
    </S.CommentWrapper>
  );
};

export default Comment;
