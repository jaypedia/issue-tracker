import { useRecoilState } from 'recoil';

import * as S from '../style';

import TabItem from '@/components/common/TabItem';
import { ISSUE_STATUS } from '@/constants/constants';
import { issueStatusState } from '@/stores/atoms/issue';
import { IssueStatusType } from '@/types/issueTypes';

type TabBarType = {
  openIssueCount?: number;
  closedIssueCount?: number;
};

const TabBar = ({ openIssueCount, closedIssueCount }: TabBarType) => {
  const [issueStatus, setIssueStatus] = useRecoilState(issueStatusState);
  const handleTabClick = (currentIssueStatus: IssueStatusType) => {
    setIssueStatus({ ...issueStatus, is: currentIssueStatus });
  };

  return (
    <S.Tabs>
      <TabItem
        isOpen
        count={openIssueCount}
        onClick={() => handleTabClick(ISSUE_STATUS.open)}
        isCurrentTab={issueStatus.is === ISSUE_STATUS.open}
      />
      <TabItem
        count={closedIssueCount}
        onClick={() => handleTabClick(ISSUE_STATUS.closed)}
        isCurrentTab={issueStatus.is === ISSUE_STATUS.closed}
      />
    </S.Tabs>
  );
};

export default TabBar;
