const path = require('path');

const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');

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
  },

  module: {
    rules: [
      {
        test: /\.(jpe?g|png|gif|mp3|svg|webg)$/,
        use: ['file-loader'],
      },
    ],
  },

  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve('public', './index.html'),
    }),
    new webpack.ProvidePlugin({ React: 'react' }),
    new ReactRefreshWebpackPlugin(),
  ],
};
