/** @type {import('next').NextConfig} */
const nextConfig = {
    env: {
        CUSTOM_VARIABLE: process.env.CUSTOM_VARIABLE || 'default_value',
    },
};

module.exports = nextConfig;
