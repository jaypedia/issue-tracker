import HeaderDropDown from './HeaderDropDown';
import * as S from './style';
import TabBar from './TabBar';

import CheckBox from '@/components/common/CheckBox';
import { HeaderDropDownList } from '@/constants/issueDropDown';
import useCheckBox from '@/hooks/useCheckBox';
import { ListHeader } from '@/styles/list';

type IssueListHeader = {
  openIssueCount: number;
  closedIssueCount: number;
};

const IssueListHeader = ({ openIssueCount, closedIssueCount }: IssueListHeader) => {
  const { isAllChecked, checkedItems, toggleAllCheckBoxes } = useCheckBox();
  const checkedItemCount = checkedItems.size;

  return (
    <ListHeader>
      <S.Flex>
        <CheckBox
          id="headerCheckBox"
          isHeader
          onChange={toggleAllCheckBoxes}
          checked={isAllChecked}
        />
        {checkedItemCount > 0 ? (
          `${checkedItemCount} selected`
        ) : (
          <TabBar openIssueCount={openIssueCount} closedIssueCount={closedIssueCount} />
        )}
      </S.Flex>
      <S.ListFilter>
        {HeaderDropDownList.map(list => (
          <S.ListFilterItem>
            <HeaderDropDown list={list} />
          </S.ListFilterItem>
        ))}
      </S.ListFilter>
    </ListHeader>
  );
};

export default IssueListHeader;
