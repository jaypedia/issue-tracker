import { COLOR } from './color';

export interface ColorType {
  headerBg: string;
  bg: string;
  inputBg: string;
  cellBg: string;
  text: string;
  lightText: string;
  line: string;
  primary: {
    [key: string]: string;
  };
  error: {
    [key: string]: string;
  };
  success: {
    [key: string]: string;
  };
  dropDown: {
    bg: {
      detailsMenuTitle: string;
      detailsMenuCell: string;
    };
    indicators: string;
    indicatorsHover: string;
    indicatorsHoverBg: string;
  };
  tabLink: {
    bg: {
      initial: string;
      hover: string;
      active: string;
    };
    font: {
      initial: string;
      active: string;
    };
  };
}

interface ThemeType {
  color: ColorType;
}

export const DARK: ThemeType = {
  color: {
    headerBg: COLOR.grey[600],
    bg: COLOR.black,
    cellBg: COLOR.grey[500],
    inputBg: COLOR.grey[700],
    text: COLOR.white,
    lightText: COLOR.grey[300],
    line: COLOR.grey[400],
    primary: COLOR.primary,
    error: COLOR.error,
    success: COLOR.success,
    dropDown: {
      bg: {
        detailsMenuTitle: COLOR.black,
        detailsMenuCell: COLOR.grey[500],
      },
      indicators: COLOR.grey[400],
      indicatorsHover: COLOR.grey[500],
      indicatorsHoverBg: COLOR.grey[200],
    },
    tabLink: {
      bg: {
        initial: COLOR.black,
        hover: COLOR.grey[600],
        active: COLOR.primary[300],
      },
      font: {
        initial: COLOR.grey[400],
        active: COLOR.white,
      },
    },
  },
};

export const LIGHT: ThemeType = {
  color: {
    headerBg: COLOR.grey[100],
    bg: COLOR.white,
    cellBg: COLOR.white,
    inputBg: COLOR.grey[100],
    text: COLOR.black,
    lightText: COLOR.grey[400],
    line: COLOR.grey[200],
    primary: COLOR.primary,
    error: COLOR.error,
    success: COLOR.success,
    dropDown: {
      bg: {
        detailsMenuTitle: COLOR.grey[100],
        detailsMenuCell: COLOR.white,
      },
      indicators: COLOR.grey[400],
      indicatorsHover: COLOR.grey[500],
      indicatorsHoverBg: COLOR.grey[200],
    },
    tabLink: {
      bg: {
        initial: COLOR.grey[100],
        hover: COLOR.grey[200],
        active: COLOR.primary[300],
      },
      font: {
        initial: COLOR.grey[400],
        active: COLOR.white,
      },
    },
  },
};
