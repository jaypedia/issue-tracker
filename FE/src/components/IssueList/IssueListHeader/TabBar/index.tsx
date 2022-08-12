import { useRecoilState } from 'recoil';

import * as S from '../style';

import CustomLink from '@/components/common/CustomLink';
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
  const handleTabClick = async (currentIssueStatus: IssueStatusType) => {
    setIssueStatus(currentIssueStatus);
  };

  return (
    <S.Tabs>
      <CustomLink
        path="?issueStatus=open"
        component={
          <TabItem
            isOpen
            count={openIssueCount}
            onClick={() => handleTabClick(ISSUE_STATUS.open)}
            isCurrentTab={issueStatus === ISSUE_STATUS.open}
          />
        }
      />
      <CustomLink
        path="?issueStatus=closed"
        component={
          <TabItem
            count={closedIssueCount}
            onClick={() => handleTabClick(ISSUE_STATUS.closed)}
            isCurrentTab={issueStatus === ISSUE_STATUS.closed}
          />
        }
      />
    </S.Tabs>
  );
};

export default TabBar;
