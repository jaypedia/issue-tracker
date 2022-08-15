import { useRecoilState, useRecoilValue } from 'recoil';

import * as S from '@/components/common/DropDown/style';
import { ISSUE_STATUS, USER } from '@/constants/constants';
import { issueStatusState } from '@/stores/atoms/issue';
import { userState } from '@/stores/atoms/user';

const detailsMenuList = {
  indicator: 'Filter',
  title: 'Filter Issues',
  menus: [
    { id: 1, name: 'Open issues', filter: ISSUE_STATUS.open },
    { id: 2, name: 'Closed issues', filter: ISSUE_STATUS.closed },
    { id: 3, name: 'Your issues', filter: ISSUE_STATUS.createdByMe },
    { id: 4, name: 'Everything assigned to you', filter: ISSUE_STATUS.assignedByMe },
    { id: 5, name: 'Everything you commented', filter: ISSUE_STATUS.commentedByMe },
  ],
};

const DetailsMenu = () => {
  const [issueStatus, setIssueStatus] = useRecoilState(issueStatusState);
  const user = useRecoilValue(userState);

  const handleMenuClick = (filter: string) => {
    if (filter === ISSUE_STATUS.open || filter === ISSUE_STATUS.closed) {
      setIssueStatus({ ...issueStatus, is: filter });
    } else if (filter === ISSUE_STATUS.createdByMe) {
      setIssueStatus({
        ...issueStatus,
        author: user?.name || USER.name,
        assignee: undefined,
        commentedBy: undefined,
      });
    } else if (filter === ISSUE_STATUS.assignedByMe) {
      setIssueStatus({
        ...issueStatus,
        assignee: user?.name || USER.name,
        author: undefined,
        commentedBy: undefined,
      });
    } else {
      setIssueStatus({
        ...issueStatus,
        commentedBy: user?.name || USER.name,
        author: undefined,
        assignee: undefined,
      });
    }
  };

  return (
    <S.DetailsMenu menuPosition="left" indicatorType="large">
      <div>
        <S.DetailsMenuTitleWrapper>
          <S.DetailsMenuTitle indicatorType="large">{detailsMenuList.title}</S.DetailsMenuTitle>
        </S.DetailsMenuTitleWrapper>
        <ul>
          {detailsMenuList.menus.map(({ id, name, filter }) => (
            <S.DetailsMenuItem indicatorType="large" key={id}>
              <button type="button" onClick={() => handleMenuClick(filter)}>
                {name}
              </button>
            </S.DetailsMenuItem>
          ))}
        </ul>
      </div>
    </S.DetailsMenu>
  );
};

export default DetailsMenu;
