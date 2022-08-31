import { rest } from 'msw';

import { mockUserData } from './data';

import { API } from '@/constants/api';

const createAccount = (req, res, ctx) => {
  const id = mockUserData[mockUserData.length - 1].id + 1;
  const userData = {
    id,
    profileImageUrl:
      'https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-mont-st-michel.jpg',
    ...req.body,
  };
  mockUserData.push(userData);
  return res(ctx.status(204));
};

const postLogin = (req, res, ctx) => {
  const { email, password } = req.body;
  const user = mockUserData.find(v => v.email === email);
  if (!user) {
    // 401 Unauthorized
    return res(ctx.status(401));
  }
  if (user.password === password) {
    return res(ctx.status(200), ctx.json(user));
  }
  return res(ctx.status(401));
};

const loginHandler = [
  rest.post(`/${API.PREFIX}/sign-up`, createAccount),
  rest.post(`/${API.PREFIX}/login`, postLogin),
];

export default loginHandler;
