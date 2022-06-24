import * as S from './style';

import CommentForm from '@/components/CommentForm';
import { ColumnWrapper, Heading1 } from '@/styles/common';

const NewIssue = () => {
  return (
    <ColumnWrapper>
      <S.NewIssueHeaderWrapper>
        <Heading1>Create a new issue</Heading1>
      </S.NewIssueHeaderWrapper>
      <CommentForm newIssue />
    </ColumnWrapper>
  );
};

export default NewIssue;
