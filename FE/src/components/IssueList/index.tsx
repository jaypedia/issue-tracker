import IssueContainer from './IssueContainer';
import IssueListHeader from './IssueListHeader';
import { IssueListType } from './type';

import { ListContainer } from '@/styles/list';

const IssueList = ({ list, openIssueCount, closedIssueCount }: IssueListType) => {
  return (
    <ListContainer>
      <IssueListHeader openIssueCount={openIssueCount} closedIssueCount={closedIssueCount} />
      <IssueContainer list={list} />
    </ListContainer>
  );
};

export default IssueList;
