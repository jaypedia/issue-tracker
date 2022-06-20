const { merge } = require('webpack-merge');
const { argv } = require('yargs');
const commonConfig = require('./webpack/common');

module.exports = () => {
  const envConfig = require(`./webpack/${argv.env}.js`);

  return merge(commonConfig, envConfig);
};
