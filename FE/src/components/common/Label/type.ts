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
