import DropDown from '../common/DropDown';
import CheckBoxIcon from './CheckBox';
import * as S from './style';

import { Closed } from '@/icons/Closed';
import { Open } from '@/icons/Open';

const authorList = {
  title: 'Filter by author',
  menus: ['Millie', 'J', 'Tany', 'geombong'],
};
const labelList = {
  title: 'Filter by label',
  menus: ['feature', 'Design'],
};
const milestoneList = {
  title: 'Filter by milestone',
  menus: ['Week 1'],
};
const assingeeList = {
  title: 'Filter by who’s assigned',
  menus: ['Millie', 'J', 'Tany', 'geombong'],
};

const IssueListHeader = () => {
  return (
    <S.IssueListHeader>
      <S.Flex>
        <CheckBoxIcon />
        <S.Tabs>
          <S.TabItem type="button">
            <Open />2 Open
          </S.TabItem>
          <S.TabItem type="button">
            <Closed />2 Closed
          </S.TabItem>
        </S.Tabs>
      </S.Flex>
      <S.ListFilter>
        <S.ListFilterItem>
          <DropDown
            indicatorType="small"
            indicatorTitle="Author"
            menuPosition="right"
            detailsMenuList={authorList}
            hasCheckBox
            checkType="checkBox"
          />
        </S.ListFilterItem>
        <S.ListFilterItem>
          <DropDown
            indicatorType="small"
            indicatorTitle="Label"
            menuPosition="right"
            detailsMenuList={labelList}
            hasCheckBox
            checkType="checkBox"
          />
        </S.ListFilterItem>
        <S.ListFilterItem>
          <DropDown
            indicatorType="small"
            indicatorTitle="Milestone"
            menuPosition="right"
            detailsMenuList={milestoneList}
            hasCheckBox
            checkType="checkBox"
          />
        </S.ListFilterItem>
        <S.ListFilterItem>
          <DropDown
            indicatorType="small"
            indicatorTitle="Assignee"
            menuPosition="right"
            detailsMenuList={assingeeList}
            hasCheckBox
            checkType="checkBox"
          />
        </S.ListFilterItem>
      </S.ListFilter>
    </S.IssueListHeader>
  );
};

export default IssueListHeader;
