import * as S from './style';
import TabBar from './TabBar';

import CheckBox from '@/components/common/CheckBox';
import DropDown from '@/components/common/DropDown';
import { mockAssignees } from '@/mocks/Assignees/data';
import { mockLabels } from '@/mocks/Labels/data';
import { mockMilestones } from '@/mocks/Milestones/data';
import { ListHeader } from '@/styles/list';

type IssueListHeader = {
  openIssueCount?: number;
  closedIssueCount?: number;
};

const authorList = {
  indicator: 'Author',
  title: 'Filter by author',
  menus: mockAssignees,
};

const labelList = {
  indicator: 'Label',
  title: 'Filter by label',
  menus: mockLabels.labels,
};

const milestoneList = {
  indicator: 'Milestone',
  title: 'Filter by milestone',
  menus: mockMilestones.milestones,
};

const assingeeList = {
  indicator: 'Assignee',
  title: 'Filter by who’s assigned',
  menus: mockAssignees,
};

const HeaderDropDownList = [authorList, labelList, milestoneList, assingeeList];

const IssueListHeader = ({ openIssueCount, closedIssueCount }: IssueListHeader) => {
  return (
    <ListHeader>
      <S.Flex>
        <CheckBox id="headerCheckBox" isHeader />
        <TabBar openIssueCount={openIssueCount} closedIssueCount={closedIssueCount} />
      </S.Flex>
      <S.ListFilter>
        {HeaderDropDownList.map(list => (
          <S.ListFilterItem>
            <DropDown
              indicatorType="small"
              indicatorTitle={list.indicator}
              menuPosition="right"
              detailsMenuList={list}
              hasCheckBox
              checkType="checkBox"
            />
          </S.ListFilterItem>
        ))}
      </S.ListFilter>
    </ListHeader>
  );
};

export default IssueListHeader;
