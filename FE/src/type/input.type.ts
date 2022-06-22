export type InputStyleProps = {
  inputStyle: 'large' | 'medium' | 'small';
};

export type InputProps = InputStyleProps & {
  title: string;
  placeholder: string;
  type: string;
  name: string;
};
