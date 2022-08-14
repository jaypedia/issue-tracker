export type UserProfileStyle = {
  size: 'large' | 'small';
};

export type UserProfileProps = UserProfileStyle & {
  imgUrl?: string;
  userId?: string;
};
