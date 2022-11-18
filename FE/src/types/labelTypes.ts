export interface LabelData {
  title: string;
  description: string;
  backgroundColor: string;
  textColor: string;
}

export interface ILabel extends LabelData {
  id: number;
}
export interface LabelDataType {
  labelCount: number;
  labels: ILabel[];
}
