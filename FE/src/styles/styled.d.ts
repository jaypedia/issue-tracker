import 'styled-components';

import { FontType } from '@/styles/font';
import { ThemeColorsType } from '@/styles/theme';

declare module 'styled-components' {
  export interface DefaultTheme {
    font: FontType;
    color: ThemeColorsType;
  }
}
