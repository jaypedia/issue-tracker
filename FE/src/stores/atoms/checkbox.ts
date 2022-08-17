import { atom } from 'recoil';

export const ATOM_CHECKBOX_KEY = 'checkBoxState';
export const ATOM_ALL_CHECKED_KEY = 'allCheckedState';

export const checkBoxState = atom({
  key: ATOM_CHECKBOX_KEY,
  default: new Set(),
});

export const isAllCheckedState = atom({
  key: ATOM_ALL_CHECKED_KEY,
  default: false,
});
