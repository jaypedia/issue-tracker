import IssueContainer from './IssueContainer';
import IssueListHeader from './IssueListHeader';

import { ListContainer } from '@/styles/list';
import { IssuesDataType } from '@/types/issueTypes';

const IssueList = ({ issues, openIssueCount, closedIssueCount }: IssuesDataType) => {
  return (
    <ListContainer>
      <IssueListHeader openIssueCount={openIssueCount} closedIssueCount={closedIssueCount} />
      <IssueContainer issues={issues} />
    </ListContainer>
  );
};

export default IssueList;
