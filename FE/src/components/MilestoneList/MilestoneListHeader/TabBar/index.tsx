import { useRecoilState } from 'recoil';

import * as S from './style';

import CustomLink from '@/components/common/CustomLink';
import TabItem from '@/components/common/TabItem';
import { MILESTONE_STATUS } from '@/constants/constants';
import { milestoneStatusState } from '@/stores/atoms/milestone';
import { IssueStatusType } from '@/types/issueTypes';

type TabBarType = {
  openCount?: number;
  closedCount?: number;
};

const TabBar = ({ openCount, closedCount }: TabBarType) => {
  const [milestoneStatus, setMilestoneStatus] = useRecoilState(milestoneStatusState);
  const handleTabClick = async (currentMilestoneStatus: IssueStatusType) => {
    setMilestoneStatus(currentMilestoneStatus);
  };

  return (
    <S.Tabs>
      <CustomLink
        path="?milestoneStatus=open"
        component={
          <TabItem
            isOpen
            count={openCount}
            onClick={() => handleTabClick(MILESTONE_STATUS.open)}
            isCurrentTab={milestoneStatus === MILESTONE_STATUS.open}
            usage="milestone"
          />
        }
      />
      <CustomLink
        path="?milestoneStatus=closed"
        component={
          <TabItem
            count={closedCount}
            onClick={() => handleTabClick(MILESTONE_STATUS.closed)}
            isCurrentTab={milestoneStatus === MILESTONE_STATUS.closed}
            usage="milestone"
          />
        }
      />
    </S.Tabs>
  );
};

export default TabBar;
