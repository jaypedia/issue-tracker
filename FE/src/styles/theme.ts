import { COLOR } from './color';

export interface ColorType {
  headerBg: string;
  bg: string;
  inputBg: string;
  cell: {
    bg: {
      cellHeaderBg: string;
      cellBg: string;
    };
    font: {
      initial: string;
      active: string;
    };
  };
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
    indicator: string;
    indicatorHover: string;
    indicatorHoverBg: string;
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
    cell: {
      bg: {
        cellHeaderBg: COLOR.grey[500],
        cellBg: COLOR.grey[400],
      },
      font: {
        initial: COLOR.grey[200],
        active: COLOR.white,
      },
    },
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
      indicator: COLOR.grey[400],
      indicatorHover: COLOR.grey[500],
      indicatorHoverBg: COLOR.grey[200],
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
    cell: {
      bg: {
        cellHeaderBg: COLOR.grey[100],
        cellBg: COLOR.white,
      },
      font: {
        initial: COLOR.grey[400],
        active: COLOR.black,
      },
    },
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
      indicator: COLOR.grey[400],
      indicatorHover: COLOR.grey[500],
      indicatorHoverBg: COLOR.grey[200],
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
