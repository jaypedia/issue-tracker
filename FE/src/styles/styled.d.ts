import 'styled-components';

import { ThemeColorsType } from '@/styles/theme';

declare module 'styled-components' {
  export interface DefaultTheme {
    color: ThemeColorsType;
  }
}
