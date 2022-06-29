import IssueItem from './IssueItem';
import { IssueListType } from './type';

const EmptyList = ({ text }: { text: string }) => {
  return <div>{text}</div>;
};

const IssueList = ({ list }: IssueListType) => {
  return (
    <ul>
      {list.map(issue => (
        <IssueItem key={issue.id} issue={issue} />
      ))}
    </ul>
  );
};

const List = ({ list }: IssueListType) => {
  return list?.length ? <IssueList list={list} /> : <EmptyList text="Welcome to issues!" />;
};

export default List;
