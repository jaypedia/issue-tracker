import React from 'react';

import * as S from './style';

type TextAreaProps = {
  name: string;
  usage: 'comment' | 'milestone';
  textareaLabel?: string;
  defaultValue?: string;
};

type I = React.ComponentPropsWithRef<'textarea'> & TextAreaProps;

const TextArea: React.FC<I> = React.forwardRef(
  ({ name, usage, textareaLabel, defaultValue }, ref) => {
    switch (usage) {
      case 'comment':
        return (
          <S.TextAreaWrapper>
            <S.TextArea
              placeholder="Leave a comment"
              name={name}
              usage={usage}
              ref={ref}
              defaultValue={defaultValue}
            />
            <S.InputFile
              accept=".gif,.jpeg,.jpg,.mov,.mp4,.png,.svg,.csv,.docx,.fodg,.fodp,.fods,.fodt,.gz,.log,.md,.odf,.odg,.odp,.ods,.odt,.pdf,.pptx,.txt,.xls,.xlsx,.zip"
              type="file"
              id="inputFile"
            />
            <S.InputLabel htmlFor="inputFile">Attach files by selecting them.</S.InputLabel>
          </S.TextAreaWrapper>
        );
      case 'milestone':
        return (
          <>
            <S.TextAreaLabel htmlFor="description">{textareaLabel}</S.TextAreaLabel>
            <S.TextAreaWrapper>
              <S.TextArea
                id="description"
                name={name}
                placeholder="Description"
                usage={usage}
                defaultValue={defaultValue}
                ref={ref}
              />
            </S.TextAreaWrapper>
          </>
        );
      default:
        return null;
    }
  },
);

export default TextArea;
