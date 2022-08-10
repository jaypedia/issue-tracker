import axios from 'axios';
import React, { useState, useRef } from 'react';

import ColorChangeButton from './ColorChangeButton';
import * as S from './style';
import { LabelFormProps } from './type';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import Label from '@/components/common/Label';
import { FORM_TYPE } from '@/constants/constants';
import { useInput } from '@/hooks/useInput';
import { FlexEndAlign, FlexBetween, FlexColumnStart } from '@/styles/common';
import { getRandomHexColorCode, getTextColor } from '@/utils/label';

const LabelForm = ({
  id,
  type,
  title = '',
  description,
  backgroundColor = getRandomHexColorCode(),
  color,
  onCancel,
}: LabelFormProps) => {
  const labelName = useRef<HTMLInputElement>(null);
  const labelDescription = useRef<HTMLInputElement>(null);

  const { value: labelPriview, onChange: changeLabelName } = useInput(title);
  const {
    value: labelColor,
    onChange: changeLabelColor,
    setValue: setLabelColor,
  } = useInput(backgroundColor.toUpperCase());
  const [labelTextColor, setLabelTextColor] = useState(color || getTextColor(backgroundColor));

  const handleChangeColorClick = () => {
    const newColor = getRandomHexColorCode();
    const textColor = getTextColor(newColor);
    setLabelColor(newColor);
    setLabelTextColor(textColor);
  };

  const saveLabel = () => {
    if (!labelName.current || !labelDescription.current) return;

    const labelData = {
      title: labelName.current.value,
      description: labelDescription.current.value,
      backgroundColor: labelColor,
      textColor: labelTextColor,
    };

    if (type === FORM_TYPE.create) {
      axios.post('/api/labels', labelData);
      return;
    }

    if (type === FORM_TYPE.edit) {
      axios.patch(`/api/labels/${id}`, labelData);
    }
  };

  return (
    <S.LabelForm type={type}>
      <FlexBetween>
        <Label
          size="small"
          title={labelPriview || 'Label Priview'}
          backgroundColor={labelColor}
          textColor={labelTextColor}
        />
        {type === FORM_TYPE.edit && <Button isText text="Delete" type="button" />}
      </FlexBetween>
      <S.GridContainer>
        <Input
          type="text"
          title="Label name"
          inputStyle="small"
          placeholder="Label name"
          name="labelName"
          hasBorder
          inputLabel="Label name"
          defaultValue={title}
          onChange={changeLabelName}
          maxLength={50}
          ref={labelName}
        />
        <Input
          type="text"
          title="Description"
          inputStyle="small"
          placeholder="Description (optional)"
          name="description"
          hasBorder
          inputLabel="Description"
          defaultValue={description}
          maxLength={100}
          ref={labelDescription}
        />
        <FlexColumnStart>
          <S.InputLabel>Color</S.InputLabel>
          <FlexEndAlign>
            <ColorChangeButton
              backgroundColor={labelColor}
              onClick={handleChangeColorClick}
              iconColor={labelTextColor}
            />
            <Input
              type="text"
              title="Color"
              inputStyle="small"
              name="color"
              hasBorder
              defaultValue={labelColor}
              onChange={changeLabelColor}
              maxLength={7}
              value={labelColor}
            />
          </FlexEndAlign>
        </FlexColumnStart>
        <S.ButtonWrapper>
          <Button size="small" color="grey" text="Cancel" type="button" onClick={onCancel} />
          <Button
            size="small"
            color="primary"
            text={type === FORM_TYPE.create ? 'Create label' : 'Save changes'}
            onClick={saveLabel}
            type="button"
          />
        </S.ButtonWrapper>
      </S.GridContainer>
    </S.LabelForm>
  );
};

export default React.memo(LabelForm);
