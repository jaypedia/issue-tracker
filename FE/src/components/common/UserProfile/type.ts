export type UserProfileStyle = {
  size: 'signUp' | 'large' | 'small';
};

export type UserProfileProps = UserProfileStyle & {
  imgUrl?: string;
  userId?: string;
  onClick?: () => void;
};
