import axios from 'axios';

export const fetchAPI = async <T>(apiName: string, params?: object): Promise<T | undefined> => {
  try {
    const response = await axios.get(`/api/${apiName}`, { params });
    return response.data;
  } catch (error) {
    console.log(error);
    return undefined;
  }
};
