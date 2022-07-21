export const LOGIN_URL = `https://github.com/login/oauth/authorize?client_id=${process.env.CLIENT_ID_GITHUB}&amp;scope=read:user%20user:email`;

export const ACCESS_TOKEN = 'accessToken';
export const REFRESH_TOKEN = 'refreshToken';
export const REFRESH_TOKEN_OPTIONS = { path: '/' };
