import Label from '../common/Label';
import UserProfile from '../common/UserProfile';
import * as S from './style';
import { CommentProps } from './type';

import { Emoji } from '@/icons/Emoji';
import { getRelativeTime } from '@/utils/issue';

const Comment = ({ issueAuthor, userId, imgUrl, createTime, description }: CommentProps) => {
  const timeCalc = getRelativeTime(createTime);
  const isIssueAuthor = issueAuthor === userId;

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
            {isIssueAuthor && (
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
