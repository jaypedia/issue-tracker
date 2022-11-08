import { useRecoilState, useRecoilValue } from 'recoil';

import { actionIssues } from '@/apis/issueApi';
import * as S from '@/components/common/DropDown/style';
import { ISSUE_STATUS } from '@/constants/constants';
import { useRefetchIssue } from '@/hooks/useIssue';
import { checkBoxState } from '@/stores/atoms/checkbox';
import { issueStatusState } from '@/stores/atoms/issue';
import { IssueStatusType } from '@/types/issueTypes';

const DetailsMenu = () => {
  const issueStatus = useRecoilValue(issueStatusState);
  const [checkBox, setCheckBox] = useRecoilState(checkBoxState);
  const { mutate } = useRefetchIssue();

  const handleMenuClick = (action: IssueStatusType) => {
    if (issueStatus.is === ISSUE_STATUS.open && action === ISSUE_STATUS.open) {
      return;
    }
    if (issueStatus.is === ISSUE_STATUS.closed && action === ISSUE_STATUS.closed) {
      return;
    }
    const ids = [...checkBox];
    actionIssues({ ids, issueStatus: action });
    setCheckBox(new Set());
    mutate();
  };

  return (
    <S.DetailsMenu menuPosition="right" indicatorType="small">
      <S.DetailsMenuTitleWrapper>
        <S.DetailsMenuTitle indicatorType="small">Actions</S.DetailsMenuTitle>
      </S.DetailsMenuTitleWrapper>
      <ul>
        <S.DetailsMenuItem indicatorType="small" onClick={() => handleMenuClick(ISSUE_STATUS.open)}>
          <S.Menu>
            <p>Open</p>
          </S.Menu>
        </S.DetailsMenuItem>
        <S.DetailsMenuItem
          indicatorType="small"
          onClick={() => handleMenuClick(ISSUE_STATUS.closed)}
        >
          <S.Menu>
            <p>Closed</p>
          </S.Menu>
        </S.DetailsMenuItem>
      </ul>
    </S.DetailsMenu>
  );
};

export default DetailsMenu;
