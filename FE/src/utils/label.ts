// Generate a random hexadecimal color code
// Reference: https://www.w3resource.com/javascript-exercises/fundamental/javascript-fundamental-exercise-11.php
export const getRandomHexColorCode = (): string => {
  const n = (Math.random() * 0xfffff * 1000000).toString(16);
  return `#${n.slice(0, 6).toUpperCase()}`;
};

// Reference: https://github.com/bgrins/TinyColor/blob/master/tinycolor.js
const getRGBNumber = (hexColorCode: string) => {
  const r = Number(`0x${hexColorCode.slice(1, 3)}`);
  const g = Number(`0x${hexColorCode.slice(3, 5)}`);
  const b = Number(`0x${hexColorCode.slice(5)}`);

  return { r, g, b };
};

const getBrightness = (hexColorCode: string) => {
  const rgb = getRGBNumber(hexColorCode);
  return (rgb.r * 299 + rgb.g * 587 + rgb.b * 114) / 1000;
};

export const isDark = (hexColorCode: string) => {
  return getBrightness(hexColorCode) < 128;
};
