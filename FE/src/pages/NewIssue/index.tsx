import * as S from './style';

import CommentBox from '@/components/CommentBox';

const NewIssue = () => {
  return (
    <S.NewIssueWrapper>
      <S.NewIssueHeaderWrapper>
        <S.Heading>Create a new issue</S.Heading>
      </S.NewIssueHeaderWrapper>
      <CommentBox newIssue />
    </S.NewIssueWrapper>
  );
};

export default NewIssue;
