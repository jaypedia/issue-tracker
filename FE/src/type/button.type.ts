type ButtonType = {
  btnSize: 'large' | 'medium' | 'small';
  btnColor: 'primary' | 'grey' | 'black';
  type?: 'button' | 'submit' | 'reset';
};

export type ButtonStyleProps = ButtonType & {
  as?: 'div';
};

export type ButtonProps = ButtonType & {
  text: string;
  changeTag?: 'div';
};
