import axios from 'axios';

export const getIssueDetail = async <T>(id: number): Promise<T | undefined> => {
  try {
    const response = await axios.get(`/api/issues/${id}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return undefined;
  }
};
