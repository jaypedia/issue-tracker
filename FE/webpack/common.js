const dotenv = require('dotenv');
const path = require('path');
const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const { argv } = require('yargs');

const isDevelopment = argv.env === 'development';

dotenv.config({ path: path.resolve('src', './.env') });
const WebpackEnvironmentPlugin = new webpack.EnvironmentPlugin([
  'OAUTH_URL_GITHUB',
  'CLIENT_ID_GITHUB',
]);

module.exports = {
  entry: {
    main: path.resolve('src', './index.tsx'),
  },

  resolve: {
    alias: {
      '@': path.resolve('src'),
    },
    modules: ['node_modules'],
    extensions: ['.js', '.ts', '.jsx', '.tsx'],
  },

  output: {
    filename: '[name].bundle.js',
    path: path.resolve('dist'),
    clean: true,
    publicPath: '/',
  },

  module: {
    rules: [
      {
        test: /\.[jt]sx?$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: [
              [
                '@babel/preset-env',
                {
                  targets: '> 0.25%, not dead',
                  useBuiltIns: 'usage',
                  corejs: { version: 3, proposals: true },
                },
              ],
              [
                '@babel/preset-react',
                {
                  runtime: 'automatic',
                },
              ],
              '@babel/preset-typescript',
            ],
            plugins: [
              isDevelopment && require.resolve('react-refresh/babel'),
              isDevelopment && 'babel-plugin-styled-components',
            ].filter(Boolean),
          },
        },
      },
      {
        test: /\.(jpe?g|png|gif|mp3|svg|woff)$/,
        type: 'asset/resource',
      },
    ],
  },

  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve('public', './index.html'),
      favicon: path.resolve('src', 'assets', './favicon.svg'),
    }),
    new webpack.ProvidePlugin({ React: 'react' }),
    isDevelopment && new ReactRefreshWebpackPlugin(),
    WebpackEnvironmentPlugin,
  ].filter(Boolean),
};
