import axios from 'axios';
import React from 'react';

import LabelForm from './LabelForm';
import * as S from './style';

import Button from '@/components/common/Button';
import Label from '@/components/common/Label';
import useBoolean from '@/hooks/useBoolean';
import { Item } from '@/styles/list';

const LabelItem = React.memo(({ id, title, backgroundColor, textColor, description }: ILabel) => {
  const { booleanState: isFormOpen, setTrue, setFalse } = useBoolean(false);

  const handleLabelEdit = () => {
    setTrue();
  };

  const handleLabelDelete = () => {
    axios.delete(`/api/labels/${id}`);
  };

  return (
    <Item key={id} type="large">
      {isFormOpen ? (
        <LabelForm
          id={id}
          type="edit"
          onCancel={setFalse}
          title={title}
          description={description}
          backgroundColor={backgroundColor}
          color={textColor}
        />
      ) : (
        <>
          <S.LabelWrapper>
            <Label
              size="small"
              title={title}
              backgroundColor={backgroundColor}
              textColor={textColor}
            />
          </S.LabelWrapper>
          <S.Description>{description}</S.Description>
          <S.ButtonsWrapper>
            <Button isText text="Edit" onClick={handleLabelEdit} />
            <Button isText text="Delete" onClick={handleLabelDelete} />
          </S.ButtonsWrapper>
        </>
      )}
    </Item>
  );
});

export default LabelItem;
