import axios from 'axios';

import { MilestoneApiData } from '@/types/milestoneTypes';

export const deleteMilestone = async (id: number) => {
  try {
    await axios.delete(`/api/milestones/${id}`);
  } catch (error) {
    console.log(error);
  }
};

export const postMilestone = async (milestoneData: MilestoneApiData) => {
  try {
    axios.post('/api/milestones', milestoneData);
  } catch (error) {
    console.log(error);
  }
};

export const patchMilestone = async (id: number, milestoneData: MilestoneApiData) => {
  try {
    axios.patch(`/api/milestones/${id}`, milestoneData);
  } catch (error) {
    console.log(error);
  }
};
