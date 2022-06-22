import * as S from './style';

type TextAreaProps = {
  name: string;
};

const TextArea = ({ name }: TextAreaProps) => {
  return (
    <S.TextAreaWrapper>
      <S.TextArea placeholder="Leave a comment" name={name} />
      <S.InputFile
        accept=".gif,.jpeg,.jpg,.mov,.mp4,.png,.svg,.csv,.docx,.fodg,.fodp,.fods,.fodt,.gz,.log,.md,.odf,.odg,.odp,.ods,.odt,.pdf,.pptx,.txt,.xls,.xlsx,.zip"
        type="file"
        id="inputFile"
      />
      <S.InputLabel htmlFor="inputFile">Attach files by selecting them.</S.InputLabel>
    </S.TextAreaWrapper>
  );
};

export default TextArea;
