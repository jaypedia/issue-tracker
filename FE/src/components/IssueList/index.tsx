import IssueContainer from './IssueContainer';
import IssueListHeader from './IssueListHeader';
import * as S from './style';
import { IssueListType } from './type';

const IssueList = ({ list, openIssueCount, closedIssueCount }: IssueListType) => {
  return (
    <S.IssueListContainer>
      <IssueListHeader openIssueCount={openIssueCount} closedIssueCount={closedIssueCount} />
      <IssueContainer list={list} />
    </S.IssueListContainer>
  );
};

export default IssueList;
