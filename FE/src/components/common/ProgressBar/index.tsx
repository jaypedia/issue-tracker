import * as S from './style';
import { ProgressBarProps } from './type';

// TODO: Milestone Link to 해당하는 Milestone으로 이동되도록 변경
const ProgressBarInfo = ({ size }: ProgressBarProps): JSX.Element => {
  switch (size) {
    case 'large':
      return <S.MilestoneStats>50% complete 2 open 7 closed</S.MilestoneStats>;
    case 'small':
      return <S.MilestoneLink to="/">Week 4</S.MilestoneLink>;
    default:
      throw new Error('Unknown size');
  }
};

const ProgressBar = ({ size }: ProgressBarProps) => {
  return (
    <S.ProgressBarWrapper size={size}>
      <S.ProgressBack size={size}>
        <S.ProgressItem size={size} />
      </S.ProgressBack>
      <ProgressBarInfo size={size} />
    </S.ProgressBarWrapper>
  );
};

export default ProgressBar;
