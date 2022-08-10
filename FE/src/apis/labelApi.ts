import axios from 'axios';

import { LabelData } from '@/types/labelTypes';

export const deleteLabel = async (id: number) => {
  try {
    await axios.delete(`/api/labels/${id}`);
  } catch (error) {
    console.log(error);
  }
};

export const postLabel = async (labelData: LabelData) => {
  try {
    axios.post('/api/labels', labelData);
  } catch (error) {
    console.log(error);
  }
};

export const patchLabel = async (id: number, labelData: LabelData) => {
  try {
    axios.patch(`/api/labels/${id}`, labelData);
  } catch (error) {
    console.log(error);
  }
};
