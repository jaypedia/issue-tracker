// Generate a random hexadecimal color code
// Reference: https://www.w3resource.com/javascript-exercises/fundamental/javascript-fundamental-exercise-11.php
export const getRandomHexColorCode = (): string => {
  const n = (Math.random() * 0xfffff * 1000000).toString(16);
  return `#${n.slice(0, 6).toUpperCase()}`;
};
