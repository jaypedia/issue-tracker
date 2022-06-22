type DropDownType = {
  menuPosition: 'left' | 'right' | 'center';
};

type detailsMenuListType = { title: string; menus: string[] };

export type DetailsMenuProps = DropDownType & {
  detailsMenuList: detailsMenuListType;
};

export type IndicatorStyle = {
  indicatorSize?: 'large';
};

type IndicatorsType = IndicatorStyle & {
  indicatorTitle: string;
};

export type DropDownProps = DetailsMenuProps & IndicatorsType;

export type DetailsMenusStyle = DropDownType;
