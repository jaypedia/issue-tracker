import React, { useState, useRef } from 'react';

import ColorChangeButton from './ColorChangeButton';
import * as S from './style';
import { LabelFormProps } from './type';

import { postLabel, editLabel } from '@/apis/labelApi';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import Label from '@/components/common/Label';
import { FORM_TYPE } from '@/constants/constants';
import { useInput } from '@/hooks/useInput';
import { useRefetchLabel } from '@/hooks/useLabel';
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
  onDelete,
}: LabelFormProps) => {
  const labelName = useRef<HTMLInputElement>(null);
  const labelDescription = useRef<HTMLInputElement>(null);
  const labelColorRef = useRef<HTMLInputElement>(null);
  const { value: labelPriview, onChange: changeName } = useInput(title);
  const {
    value: labelColor,
    onChange: changeColor,
    setValue: setLabelColor,
  } = useInput(backgroundColor.toUpperCase());
  const [labelTextColor, setLabelTextColor] = useState(color || getTextColor(backgroundColor));
  const { mutate } = useRefetchLabel();
  const [isButtonDisabled, setIsButtonDisabled] = useState(type === FORM_TYPE.create);

  const handleInputChange = () => {
    if (!labelName.current || !labelDescription.current) return;

    if (labelName.current.value && labelDescription.current.value) {
      setIsButtonDisabled(false);
    } else {
      setIsButtonDisabled(true);
    }
  };

  const handleChangeColorClick = () => {
    const newColor = getRandomHexColorCode();
    const textColor = getTextColor(newColor);
    setLabelColor(newColor);
    setLabelTextColor(textColor);
    handleInputChange();
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
      postLabel(labelData);
      mutate();
      onCancel();
      return;
    }

    if (type === FORM_TYPE.edit && id) {
      editLabel(id, labelData);
      mutate();
      onCancel();
    }
  };

  const changeLabelName = (e: React.ChangeEvent<HTMLInputElement>) => {
    changeName(e);
    handleInputChange();
  };

  const changeLabelColor = (e: React.ChangeEvent<HTMLInputElement>) => {
    changeColor(e);
    const regex = /^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/;
    const colorCode = e.target.value;
    if (regex.test(colorCode)) {
      const textColor = getTextColor(colorCode);
      setLabelTextColor(textColor);
      setIsButtonDisabled(false);
    } else {
      setIsButtonDisabled(true);
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
        {type === FORM_TYPE.edit && (
          <Button isText text="Delete" type="button" onClick={onDelete} />
        )}
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
          onChange={handleInputChange}
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
              type="Hex colors should only contain numbers and letters from a-f"
              title="Color"
              inputStyle="small"
              name="color"
              hasBorder
              defaultValue={labelColor}
              onChange={changeLabelColor}
              maxLength={7}
              value={labelColor}
              ref={labelColorRef}
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
            disabled={isButtonDisabled}
          />
        </S.ButtonWrapper>
      </S.GridContainer>
    </S.LabelForm>
  );
};

export default React.memo(LabelForm);
