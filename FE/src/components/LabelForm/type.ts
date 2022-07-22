export type FormStyleType = {
  type: 'create' | 'edit';
};

export type LabelFormProps = FormStyleType & {
  labelName?: string;
  description?: string;
  backgroundColor: string;
  color?: string;
  onCancel?: () => void;
};

export type ColorChangeButtonProps = {
  backgroundColor: string;
  onClick: () => void;
};
