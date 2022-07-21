type DropDownType = {
  menuPosition: 'left' | 'right' | 'center';
  indicatorType?: 'large' | 'small' | 'setting';
  hasCheckBox?: boolean;
  checkType?: 'checkBox' | 'radio';
};

export type detailsMenuListType = { title: string; menus: string[] };

export type DetailsMenuProps = DropDownType & {
  detailsMenuList: detailsMenuListType;
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
