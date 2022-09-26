import { IMilestone } from '@/types/milestoneTypes';

export const filterMilestones = (queryString: string, milestones: IMilestone[]) => {
  if (!queryString) return milestones;
  const searchParams = new URLSearchParams(queryString);
  const currentState = searchParams.get('is');
  return milestones.filter(m => m.milestoneStatus === currentState);
};
