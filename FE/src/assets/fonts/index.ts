import NotoSansBold from './NotoSans-Bold.woff';
import NotoSansMedium from './NotoSans-Medium.woff';
import NotoSansRegular from './NotoSans-Regular.woff';

const fonts = `
  @font-face {
    font-family: 'Noto Sans';
    font-style: normal;
    font-weight: 700;
    src: local('NotoSans-Bold'), url(${NotoSansBold}) format('woff');
  }

  @font-face {
    font-family: 'Noto Sans';
    font-style: normal;
    font-weight: 500;
    src: local('NotoSans-Bold'), url(${NotoSansMedium}) format('woff');
  }

  @font-face {
    font-family: 'Noto Sans';
    font-style: normal;
    font-weight: 400;
    src: local('NotoSans-Bold'), url(${NotoSansRegular}) format('woff');
  }
`;

export default fonts;
