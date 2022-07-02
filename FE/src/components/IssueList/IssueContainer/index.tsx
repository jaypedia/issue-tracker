import IssueItem from '../IssueItem';
import * as S from '../style';
import { IssueListType } from '../type';

const EmptyList = ({ text }: { text: string }) => {
  return <S.EmptyList>{text}</S.EmptyList>;
};

const IssueItems = ({ list }: IssueListType) => {
  return (
    <ul>
      {list.map(issue => (
        <IssueItem key={issue.id} issue={issue} />
      ))}
    </ul>
  );
};

const IssueContainer = ({ list }: IssueListType) => {
  return list?.length ? <IssueItems list={list} /> : <EmptyList text="Welcome to issues!" />;
};

export default IssueContainer;
