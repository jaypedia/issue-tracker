type ButtonType = {
  size?: 'large' | 'medium' | 'small';
  textColor?: 'primary' | 'grey' | 'warning';
  color?: 'primary' | 'grey' | 'black';
  type?: 'button' | 'submit' | 'reset';
  isText?: boolean;
  onClick?: () => void;
};

export type ButtonStyleProps = ButtonType & {
  as?: 'div';
};

export type ButtonProps = ButtonType & {
  text: string;
  changeTag?: 'div';
};
