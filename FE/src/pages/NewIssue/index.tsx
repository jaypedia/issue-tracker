import * as S from './style';

import NewIssueForm from '@/components/NewIssueForm';
import { ColumnWrapper, Heading1 } from '@/styles/common';

const NewIssue = () => {
  return (
    <ColumnWrapper>
      <S.NewIssueHeaderWrapper>
        <Heading1>Create a new issue</Heading1>
      </S.NewIssueHeaderWrapper>
      <NewIssueForm />
    </ColumnWrapper>
  );
};

export default NewIssue;
