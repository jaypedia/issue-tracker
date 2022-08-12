import IssueItem from '../IssueItem';
import { IssueListType } from '../type';
import * as S from './style';

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
