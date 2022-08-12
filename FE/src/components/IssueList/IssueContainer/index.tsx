import IssueItem from '../IssueItem';
import * as S from './style';

import { IssueType } from '@/types/issueTypes';

const EmptyList = ({ text }: { text: string }) => {
  return <S.EmptyList>{text}</S.EmptyList>;
};

const IssueItems = ({ issues }: { issues: IssueType[] }) => {
  return (
    <ul>
      {issues.map(issue => (
        <IssueItem key={issue.id} issue={issue} />
      ))}
    </ul>
  );
};

const IssueContainer = ({ issues }: { issues: IssueType[] }) => {
  return issues?.length ? <IssueItems issues={issues} /> : <EmptyList text="Welcome to issues!" />;
};

export default IssueContainer;
