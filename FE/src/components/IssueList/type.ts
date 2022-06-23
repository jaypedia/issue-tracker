export type Label = {
  id: number;
  title: string;
  backgroundColor: string;
  textColor: string;
};

export type Assignees = {
  id: number;
  image: string;
};

type IssueItem = {
  id: number;
  issueTitle: string;
  author: string;
  issueCreateTime: string;
  labels: Label[];
  mileStoneTitle: string;
  assignees: Assignees[];
  issueWriterImage: string;
  issueStatus: string;
};

export type IssueItemType = {
  issue: IssueItem;
};

export type IssueListType = {
  list: IssueItem[] | [];
};
