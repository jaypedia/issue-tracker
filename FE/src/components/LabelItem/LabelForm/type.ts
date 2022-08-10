export type FormStyleType = {
  type: 'create' | 'edit';
};

export type LabelFormProps = FormStyleType & {
  id?: number;
  title?: string;
  description?: string;
  backgroundColor?: string;
  color?: string;
  onCancel?: () => void;
};

export type ColorChangeButtonProps = {
  backgroundColor: string;
  onClick: () => void;
  iconColor?: string | undefined;
};
