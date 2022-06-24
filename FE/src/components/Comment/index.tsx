import Label from '../common/Label';
import UserProfile from '../common/UserProfile';
import * as S from './style';
import { CommentProps } from './type';

import { Emoji } from '@/icons/emoji';
import { getRelativeTime } from '@/utils/issue';

const Comment = ({ issueAuthor, userId, imgUrl, createTime, description }: CommentProps) => {
  const timeCalc = getRelativeTime(createTime);
  const isIssueAuthor = issueAuthor === userId;

  return (
    <S.CommentWraaper>
      <UserProfile size="large" imgUrl={imgUrl} userId={userId} />
      <S.CommentContainer>
        <S.CommentHeader>
          <S.Flex>
            <S.UserId>{userId}</S.UserId>
            <S.Time>{timeCalc}</S.Time>
          </S.Flex>
          <S.Flex>
            {isIssueAuthor && (
              <>
                <Label size="small" title="Author" backgroundColor="none" hasLine />
                <S.EditBtn type="button">edit</S.EditBtn>
              </>
            )}
            <S.EmojiBtn type="button">
              <Emoji />
            </S.EmojiBtn>
          </S.Flex>
        </S.CommentHeader>
        <S.CommentContents>{description || 'No description provided.'}</S.CommentContents>
      </S.CommentContainer>
    </S.CommentWraaper>
  );
};

export default Comment;
