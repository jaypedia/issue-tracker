import * as S from './style';
import { LabelFormProps, ColorChangeButtonProps } from './type';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import Label from '@/components/common/Label';
import { useInput } from '@/hooks/useInput';
import * as I from '@/icons/Label';
import { FlexEndAlign, FlexBetween, FlexColumnStart } from '@/styles/common';

const randomColor = '#bfdadc';

const ColorChangeButton = ({ backgroundColor }: ColorChangeButtonProps) => {
  return (
    <S.ColorChangeButton backgroundColor={backgroundColor} type="button">
      <I.Change />
    </S.ColorChangeButton>
  );
};

const LabelForm = ({
  type,
  labelName,
  description,
  backgroundColor,
  color,
  onCancel,
}: LabelFormProps) => {
  const saveLabel = () => {
    // Api logic
  };

  const { value: labelPriview, onChange } = useInput(labelName);

  return (
    <S.LabelForm type={type}>
      <FlexBetween>
        <Label
          size="small"
          title={labelPriview || 'Label preview'}
          backgroundColor={backgroundColor || randomColor}
          textColor={color}
        />
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
          onChange={onChange}
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
            <ColorChangeButton backgroundColor={backgroundColor || randomColor} />
            <Input
              type="text"
              title="Color"
              inputStyle="small"
              name="color"
              hasBorder
              defaultValue={backgroundColor || randomColor}
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

export default LabelForm;
