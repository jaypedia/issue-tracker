import * as S from './style';
import { ProgressBarProps, ProgressBarInfoProps } from './type';

// TODO: Milestone Link to 해당하는 Milestone으로 이동되도록 변경
const ProgressBarInfo = ({
  size,
  openIssueCount,
  closedIssueCount,
  percent,
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
      return <S.MilestoneLink to="/">Week 4</S.MilestoneLink>;
    default:
      throw new Error('Unknown size');
  }
};

const ProgressBar = ({ size, openIssueCount, closedIssueCount }: ProgressBarProps) => {
  const percent = Math.floor((closedIssueCount / (openIssueCount + closedIssueCount)) * 100);

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
      />
    </S.ProgressBarWrapper>
  );
};

export default ProgressBar;
