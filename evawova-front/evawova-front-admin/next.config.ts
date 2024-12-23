const withLess = require('next-with-less');

module.exports = withLess({
    reactStrictMode: true,
    lessLoaderOptions: {
        javascriptEnabled: true,
    },
});