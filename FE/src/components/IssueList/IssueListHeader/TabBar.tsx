import axios from 'axios';

import * as S from './style';

import CustomLink from '@/components/common/CustomLink';
import { ISSUE_STATUS } from '@/constants/constants';
import ClosedIcon from '@/icons/Closed';
import OpenIcon from '@/icons/Open';

type TabItemType = {
  isOpen?: boolean;
  issueCount?: number;
  onClick: () => void;
};

type TabBarType = {
  openIssueCount?: number;
  closedIssueCount?: number;
};

const handleTabClick = async (issueStatus: string) => {
  const response = await axios.get(`/api/issues?issueStatus=${issueStatus}`);
  console.log(response.data);
};

const TabItem = ({ isOpen, issueCount, onClick }: TabItemType) => {
  return (
    <S.TabItem type="button" onClick={onClick}>
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
  return (
    <S.Tabs>
      <CustomLink
        path="?issueStatus=open"
        component={
          <TabItem
            isOpen
            issueCount={openIssueCount}
            onClick={() => handleTabClick(ISSUE_STATUS.open)}
          />
        }
      />
      <CustomLink
        path="?issueStatus=closed"
        component={
          <TabItem
            issueCount={closedIssueCount}
            onClick={() => handleTabClick(ISSUE_STATUS.closed)}
          />
        }
      />
    </S.Tabs>
  );
};

export default TabBar;
