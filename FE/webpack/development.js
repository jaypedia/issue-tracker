const path = require('path');

const webpack = require('webpack');

const PORT = 9000;

module.exports = {
  mode: 'development',

  devtool: 'eval-source-map',

  module: {
    rules: [
      {
        test: /\.(sc|c|sa)ss$/,
        use: ['style-loader', 'css-loader'],
      },
      {
        test: /\.(ts|tsx|js|jsx)$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      },
    ],
  },

  plugins: [new webpack.ProgressPlugin()],

  devServer: {
    client: {
      overlay: {
        errors: true,
        warnings: false,
      },
      progress: true,
    },
    compress: true,
    historyApiFallback: true,
    static: {
      directory: path.join(__dirname, 'public'),
    },
    open: true,
    port: PORT,
  },
};
