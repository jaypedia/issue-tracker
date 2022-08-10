import axios from 'axios';

import { API } from '@/constants/api';

type API = 'issues' | 'labels' | 'milestones';

export const fetchAPI = async <T>(apiName: API, params?: object): Promise<T | undefined> => {
  try {
    const response = await axios.get(`/api/${apiName}`, { params });
    return response.data;
  } catch (error) {
    console.log(error);
    return undefined;
  }
};
