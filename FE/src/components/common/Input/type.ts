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
  defaultValue?: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  maxLength?: number;
  pattern?: string;
};
