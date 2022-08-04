import { useRecoilState } from 'recoil';

import * as S from './style';

import CustomLink from '@/components/common/CustomLink';
import { ISSUE_STATUS, IssueStatusType } from '@/constants/constants';
import ClosedIcon from '@/icons/Closed';
import OpenIcon from '@/icons/Open';
import { issueStatusState } from '@/stores/atoms/issue';

type TabItemType = {
  isOpen?: boolean;
  issueCount?: number;
  isCurrentTab: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

type TabBarType = {
  openIssueCount?: number;
  closedIssueCount?: number;
};

const TabItem = ({ isOpen, issueCount, onClick, isCurrentTab }: TabItemType) => {
  return (
    <S.TabItem type="button" onClick={onClick} isCurrentTab={isCurrentTab}>
      {isOpen ? (
        <>
          <OpenIcon />
          {issueCount} Open
        </>
      ) : (
        <>
          <ClosedIcon />
          {issueCount} Closed
        </>
      )}
    </S.TabItem>
  );
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
            issueCount={openIssueCount}
            onClick={() => handleTabClick(ISSUE_STATUS.open)}
            isCurrentTab={issueStatus === ISSUE_STATUS.open}
          />
        }
      />
      <CustomLink
        path="?issueStatus=closed"
        component={
          <TabItem
            issueCount={closedIssueCount}
            onClick={() => handleTabClick(ISSUE_STATUS.closed)}
            isCurrentTab={issueStatus === ISSUE_STATUS.closed}
          />
        }
      />
    </S.Tabs>
  );
};

export default TabBar;
