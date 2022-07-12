export type ProgressBarProps = {
  size: 'large' | 'small';
  openIssueCount: number;
  closedIssueCount: number;
};

export type ProgressBarInfoProps = ProgressBarProps & {
  percent: number;
};

type Optional<T> = {
  [P in keyof T]?: T[P];
};

export type ProgressBarOptional = Optional<ProgressBarInfoProps>;
