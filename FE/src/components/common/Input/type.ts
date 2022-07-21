export type InputStyleProps = {
  inputStyle: 'large' | 'medium' | 'small';
  hasBorder?: boolean;
  inputLabel?: string;
};

export type InputProps = InputStyleProps & {
  title: string;
  placeholder?: string;
  type: string;
  name: string;
  value?: string;
};
