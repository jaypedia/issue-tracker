import { HeaderDropDownList } from './data';
import * as S from './style';
import TabBar from './TabBar';

import CheckBox from '@/components/common/CheckBox';
import DropDown from '@/components/common/DropDown';

type IssueListHeader = {
  openIssueCount?: number;
  closedIssueCount?: number;
};

const IssueListHeader = ({ openIssueCount, closedIssueCount }: IssueListHeader) => {
  return (
    <S.IssueListHeader>
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
    </S.IssueListHeader>
  );
};

export default IssueListHeader;
