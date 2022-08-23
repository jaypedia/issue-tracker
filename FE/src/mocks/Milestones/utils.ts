import { MilestoneType } from '@/types/milestoneTypes';

export const filterMilestones = (queryString: string, milestones: MilestoneType[]) => {
  if (!queryString) return milestones;
  const searchParams = new URLSearchParams(queryString);
  const currentState = searchParams.get('params');
  return milestones.filter(m => m.milestoneStatus === currentState);
};
