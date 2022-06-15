export const mixins = {
  flexBox: ({ direction = 'row', alignItems = 'center', justifyContent = 'center' }) => `
          display: flex;
          flex-direction: ${direction};
          align-items: ${alignItems};
          justify-content: ${justifyContent};
        `,
};
