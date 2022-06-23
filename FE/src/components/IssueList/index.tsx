import IssueListHeader from './IssueListHeader';
import List from './List';
import * as S from './style';
import { IssueListType } from './type';

const IssueList = ({ list }: IssueListType) => {
  return (
    <S.IssueListContainer>
      <IssueListHeader />
      <List list={list} />
    </S.IssueListContainer>
  );
};

export default IssueList;
