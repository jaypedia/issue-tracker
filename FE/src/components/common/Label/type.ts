// Temporary
export interface ILabel {
  id: number;
  title: string;
  backgroundColor: string;
  textColor: string;
  description: string;
}

export type LabelProps = {
  title: string;
  size: 'large' | 'small';
  backgroundColor: string;
  textColor?: string;
  hasLine?: boolean;
  icon?: JSX.Element;
};

export type LabelStyle = {
  size: 'large' | 'small';
  backgroundColor: string;
  textColor?: string;
  hasLine?: boolean;
};
