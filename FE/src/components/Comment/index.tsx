import Label from '../common/Label';
import UserProfile from '../common/UserProfile';
import * as S from './style';

import { Emoji } from '@/icons/Emoji';
import { getRelativeTime } from '@/utils/issue';

export type CommentProps = {
  issueAuthor: string | false;
  imgUrl: string;
  userId: string;
  createdAt: string;
  description: string;
};

const Comment = ({ issueAuthor, userId, imgUrl, createdAt, description }: CommentProps) => {
  const timeCalc = getRelativeTime(createdAt);

  return (
    <S.CommentWrapper>
      <UserProfile size="large" imgUrl={imgUrl} userId={userId} />
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
                <S.EditButton type="button">edit</S.EditButton>
              </>
            )}
            <S.EmojiButton type="button">
              <Emoji />
            </S.EmojiButton>
          </S.Flex>
        </S.CommentHeader>
        <S.CommentContents>{description || 'No description provided.'}</S.CommentContents>
      </S.CommentContainer>
    </S.CommentWrapper>
  );
};

export default Comment;
