import { font } from './font';

export const mixins = {
  flexBox: ({ direction = 'row', alignItems = 'center', justifyContent = 'center' }) => `
          display: flex;
          flex-direction: ${direction};
          align-items: ${alignItems};
          justify-content: ${justifyContent};
        `,
};

export type FontWeight = 400 | 500 | 700;

export interface FontMixinType {
  [key: string]: (fontWeight: FontWeight) => string;
}

export const FONT_MIXIN: FontMixinType = {
  display: (fontWeight: FontWeight) => `
  font-size: ${font.size.display};
  line-height: ${font.lineHeight.display};
  font-weight: ${fontWeight};
`,
  large: (fontWeight: FontWeight) => `
  font-size: ${font.size.large};
  line-height: ${font.lineHeight.large};
  font-weight: ${fontWeight};
`,

  medium: (fontWeight: FontWeight) => `
  font-size: ${font.size.medium};
  line-height: ${font.lineHeight.medium};
  font-weight: ${fontWeight};
`,

  small: (fontWeight: FontWeight) => `
  font-size: ${font.size.small};
  line-height: ${font.lineHeight.small};
  font-weight: ${fontWeight};
`,

  xSmall: (fontWeight: FontWeight) => `
  font-size: ${font.size.xSmall};
  line-height: ${font.lineHeight.xSmall};
  font-weight: ${fontWeight};
`,
};
