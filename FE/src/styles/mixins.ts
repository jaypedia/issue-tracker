import { font } from './font';

export const mixins = {
  flexBox: ({ direction = 'row', alignItems = 'center', justifyContent = 'center' }) => `
          display: flex;
          flex-direction: ${direction};
          align-items: ${alignItems};
          justify-content: ${justifyContent};
        `,
};

type FontWeight = 400 | 500 | 700;

export const displayFont = (fontWeight: FontWeight) => `
  font-size: ${font.size.display};
  line-height: ${font.lineHeight.display};
  font-weight: ${fontWeight};
`;

export const largeFont = (fontWeight: FontWeight) => `
  font-size: ${font.size.large};
  line-height: ${font.lineHeight.large};
  font-weight: ${fontWeight};
`;

export const mediumFont = (fontWeight: FontWeight) => `
  font-size: ${font.size.medium};
  line-height: ${font.lineHeight.medium};
  font-weight: ${fontWeight};
`;

export const smallFont = (fontWeight: FontWeight) => `
  font-size: ${font.size.small};
  line-height: ${font.lineHeight.small};
  font-weight: ${fontWeight};
`;

export const xSmallFont = (fontWeight: FontWeight) => `
  font-size: ${font.size.xSmall};
  line-height: ${font.lineHeight.xSmall};
  font-weight: ${fontWeight};
`;
