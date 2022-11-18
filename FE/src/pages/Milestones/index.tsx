import { useState } from 'react';
import { useRecoilValue } from 'recoil';

import Loading from '@/components/common/Loading';
import MilestoneList from '@/components/MilestoneList';
import MilestoneForm from '@/components/MilestoneList/MilestoneItem/MilestoneForm';
import Navbar from '@/components/Navbar';
import useBoolean from '@/hooks/useBoolean';
import { useGetMilestone } from '@/hooks/useMilestone';
import useMovePage from '@/hooks/useMovePage';
import { milestoneStatusState } from '@/stores/atoms/milestone';
import { MainWrapper, InnerContainer } from '@/styles/common';
import { ListContainer } from '@/styles/list';
import { IMilestone } from '@/types/milestoneTypes';

const Milestones = () => {
  const milestoneStatus = useRecoilValue(milestoneStatusState);
  const { data, isLoading } = useGetMilestone(milestoneStatus);
  const [goNewMilestone] = useMovePage('/newMilestone');
  const { booleanState: isFormOpen, setTrue, setFalse } = useBoolean(false);
  const [selectedMilestone, setSelectedMilestone] = useState<IMilestone | undefined>(undefined);

  const editMilestone = (milestone: IMilestone) => {
    setTrue();
    setSelectedMilestone(milestone);
  };

  return (
    <MainWrapper>
      <InnerContainer>
        <Navbar btnText="New Milestone" onClick={goNewMilestone} />
        {isFormOpen && data ? (
          <MilestoneForm data={selectedMilestone} type="edit" onCancel={setFalse} />
        ) : (
          <ListContainer>
            {isLoading || !data ? (
              <Loading />
            ) : (
              <MilestoneList data={data} onEdit={editMilestone} />
            )}
          </ListContainer>
        )}
      </InnerContainer>
    </MainWrapper>
  );
};

export default Milestones;
