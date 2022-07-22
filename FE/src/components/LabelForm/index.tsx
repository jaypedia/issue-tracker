import React from 'react';

import ColorChangeButton from './ColorChangeButton';
import * as S from './style';
import { LabelFormProps } from './type';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import Label from '@/components/common/Label';
import { useInput } from '@/hooks/useInput';
import { FlexEndAlign, FlexBetween, FlexColumnStart } from '@/styles/common';
import { getRandomHexColorCode } from '@/utils/label';

const LabelForm = ({
  type,
  labelName = 'Label preview',
  description,
  backgroundColor = getRandomHexColorCode(),
  color,
  onCancel,
}: LabelFormProps) => {
  const { value: labelPriview, onChange: changeLabelName } = useInput(labelName);
  const {
    value: labelColor,
    onChange: changeLabelColor,
    setValue: setLabelColor,
  } = useInput(backgroundColor.toUpperCase());

  const handleChangeColorClick = () => {
    const newColor = getRandomHexColorCode();
    setLabelColor(newColor);
  };

  const saveLabel = () => {
    // Api logic
  };

  return (
    <S.LabelForm type={type}>
      <FlexBetween>
        <Label size="small" title={labelPriview} backgroundColor={labelColor} textColor={color} />
        {type === 'edit' && <Button isText text="Delete" type="button" />}
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
          defaultValue={labelName}
          onChange={changeLabelName}
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
        />
        <FlexColumnStart>
          <S.InputLabel>Color</S.InputLabel>
          <FlexEndAlign>
            <ColorChangeButton backgroundColor={labelColor} onClick={handleChangeColorClick} />
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
            text={type === 'create' ? 'Create label' : 'Save changes'}
            onClick={saveLabel}
          />
        </S.ButtonWrapper>
      </S.GridContainer>
    </S.LabelForm>
  );
};

export default React.memo(LabelForm);
