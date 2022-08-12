import * as S from './style';
import { ProgressBarProps, ProgressBarInfoProps } from './type';

const ProgressBarInfo = ({
  size,
  openIssueCount,
  closedIssueCount,
  percent,
  title,
}: ProgressBarInfoProps): JSX.Element => {
  switch (size) {
    case 'large':
      return (
        <S.MilestoneStats>
          <p>{percent}% complete</p>
          <div>
            <S.MilestoneLink to="/">{openIssueCount} open</S.MilestoneLink>
            <S.MilestoneLink to="/">{closedIssueCount} closed</S.MilestoneLink>
          </div>
        </S.MilestoneStats>
      );
    case 'small':
      return <S.MilestoneLink to="/milestones">{title}</S.MilestoneLink>;
    default:
      throw new Error('Unknown size');
  }
};

const ProgressBar = ({ size, openIssueCount, closedIssueCount, title }: ProgressBarProps) => {
  const percent = Math.floor((closedIssueCount / (openIssueCount + closedIssueCount)) * 100) || 0;

  return (
    <S.ProgressBarWrapper size={size}>
      <S.ProgressBack size={size}>
        <S.ProgressItem size={size} percent={percent} />
      </S.ProgressBack>
      <ProgressBarInfo
        size={size}
        openIssueCount={openIssueCount}
        closedIssueCount={closedIssueCount}
        percent={percent}
        title={title}
      />
    </S.ProgressBarWrapper>
  );
};

export default ProgressBar;
