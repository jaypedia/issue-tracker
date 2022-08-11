import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import Loading from '@/components/common/Loading';
import MilestoneList from '@/components/MilestoneList';
import MilestoneForm from '@/components/MilestoneList/MilestoneItem/MilestoneForm';
import Navbar from '@/components/Navbar';
import useBoolean from '@/hooks/useBoolean';
import { useGetMilestone } from '@/hooks/useMilestone';
import { milestoneStatusState } from '@/stores/atoms/milestone';
import { MainWrapper, InnerContainer } from '@/styles/common';
import { ListContainer } from '@/styles/list';

const Milestones = () => {
  const milestoneStatus = useRecoilValue(milestoneStatusState);
  const { data, isLoading } = useGetMilestone(milestoneStatus);
  const navigate = useNavigate();
  const { booleanState: isFormOpen, setTrue, setFalse } = useBoolean(false);

  return (
    <MainWrapper>
      <InnerContainer>
        <Navbar btnText="New Milestone" onClick={() => navigate('/newMilestone')} />
        {isFormOpen ? (
          <MilestoneForm type="edit" onCancel={setFalse} />
        ) : (
          <ListContainer>
            {isLoading || !data ? <Loading /> : <MilestoneList data={data} onEdit={setTrue} />}
          </ListContainer>
        )}
      </InnerContainer>
    </MainWrapper>
  );
};

export default Milestones;
