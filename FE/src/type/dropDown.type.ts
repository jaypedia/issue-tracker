type DropDownType = {
  menuPosition: 'left' | 'right' | 'center';
};

type detailsMenuListType = { title: string; menus: string[] };

export type DetailsMenuProps = DropDownType & {
  detailsMenuList: detailsMenuListType;
};

export type IndicatorsSytle = {
  IndicatorsSize?: 'large';
};

type IndicatorsType = IndicatorsSytle & {
  IndicatorsTitle: string;
};

export type DropDownProps = DetailsMenuProps & IndicatorsType;

export type DetailsMenusSytle = DropDownType;
