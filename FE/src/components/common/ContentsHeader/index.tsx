import * as S from './style';

type ContentsHeaderProps = {
  headingText: string;
};

const ContentsHeader = ({ headingText }: ContentsHeaderProps) => {
  return (
    <S.ContentsHeaderWrapper>
      <S.ContentsTopBox>
        <S.Heading>{headingText}</S.Heading>
      </S.ContentsTopBox>
    </S.ContentsHeaderWrapper>
  );
};

export default ContentsHeader;
