module.exports = {
  baseUrl: '/',
  devServer: {
    // open: process.platform === 'darwin',
    // host: '0.0.0.0',
    port: 7001,
    https: false,
    hotOnly: false,
    proxy: null,
    before: app => {}
  }
}
