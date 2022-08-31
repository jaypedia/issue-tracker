import React from 'react';

import * as S from './style';
import { InputProps } from './type';

type I = React.ComponentPropsWithRef<'input'> & InputProps;

const Input: React.FC<I> = React.forwardRef((inputData, ref) => {
  const {
    inputLabel,
    type,
    title,
    placeholder,
    inputStyle,
    hasBorder,
    name,
    defaultValue,
    value,
    onChange,
    maxLength,
    pattern,
  } = inputData;

  return (
    <S.InputLabel>
      {inputLabel}
      <S.Input
        type={type}
        title={title}
        placeholder={placeholder}
        inputStyle={inputStyle}
        hasBorder={hasBorder}
        name={name}
        defaultValue={defaultValue}
        value={value}
        onChange={onChange}
        maxLength={maxLength}
        ref={ref}
        pattern={pattern}
        autoComplete="off"
      />
    </S.InputLabel>
  );
});

export default Input;
