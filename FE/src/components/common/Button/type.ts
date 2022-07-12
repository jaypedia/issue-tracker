type ButtonType = {
  btnStyle: 'large' | 'medium' | 'small' | 'text';
  textColor?: 'primary' | 'grey' | 'warning';
  btnColor?: 'primary' | 'grey' | 'black';
  type?: 'button' | 'submit' | 'reset';
  onClick?: () => void;
};

export type ButtonStyleProps = ButtonType & {
  as?: 'div';
};

export type ButtonProps = ButtonType & {
  text: string;
  changeTag?: 'div';
};
