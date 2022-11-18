import axios from 'axios';

type UserDataType = {
  name?: string;
  email: string;
  password: string;
};

export type MockUserDataType = UserDataType & {
  id: number;
  profileImageUrl: string;
};

export const createAccount = async (userData: UserDataType) => {
  try {
    axios.post(`/api/sign-up`, userData);
  } catch (error) {
    console.log(error);
  }
};

export const postLogin = async <T>(userData: UserDataType): Promise<T | undefined> => {
  try {
    const response = await axios.post(`/api/login`, userData);
    return response.data;
  } catch (error) {
    console.log(error);
    return undefined;
  }
};
