import * as S from './style';
import { ProgressBarProps, ProgressBarInfoProps } from './type';

// TODO: Milestone Link to 해당하는 이슈만 보여줄 수 있는 필터링 기능
// small - 해당 마일스톤 디테일 페이지로 이동
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
      return <S.MilestoneLink to="/milestones">Week 4</S.MilestoneLink>;
    default:
      throw new Error('Unknown size');
  }
};

const ProgressBar = ({ size, openIssueCount, closedIssueCount }: ProgressBarProps) => {
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
      />
    </S.ProgressBarWrapper>
  );
};

export default ProgressBar;
