import { IndicatorTitleType } from '@/hooks/useSideBar';
// TODO: Refactoring types
type DropDownType = {
  menuPosition: 'left' | 'right' | 'center';
  indicatorType?: 'large' | 'small' | 'setting';
  hasCheckBox?: boolean;
  checkType?: 'checkBox' | 'radio';
};

export type detailsMenuListType = { indicator: string; title: string };

export type DetailsMenuProps = DropDownType & {
  detailsMenuList: detailsMenuListType;
  title: string;
  indicatorTitle: IndicatorTitleType;
};

export type IndicatorStyle = {
  indicatorType?: 'large' | 'small' | 'setting';
  hasBefore?: boolean;
};

type IndicatorType = IndicatorStyle & {
  indicatorTitle: string;
};

export type DropDownProps = DetailsMenuProps & IndicatorType;

export type DetailsMenusStyle = DropDownType;
