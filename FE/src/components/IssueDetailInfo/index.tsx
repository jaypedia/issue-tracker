import * as S from './style';

import Label from '@/components/common/Label';
import { ISSUE_STATUS } from '@/constants/constants';
import ClosedIcon from '@/icons/Closed';
import OpenIcon from '@/icons/Open';
import { COLOR } from '@/styles/color';
import { convertFirstLetterToUppercase, getIssueInfoSentence } from '@/utils/issue';

const IssueDetailInfo = ({ issueId, issueStatus, author, issueCreateTime, commentCount }) => {
  return (
    <S.IssueInfoBox>
      <Label
        title={convertFirstLetterToUppercase(issueStatus)}
        size="large"
        backgroundColor={
          issueStatus === ISSUE_STATUS.open ? COLOR.success[300] : COLOR.primary[200]
        }
        textColor={COLOR.white}
        icon={issueStatus === ISSUE_STATUS.open ? <OpenIcon /> : <ClosedIcon />}
      />
      <p>
        {getIssueInfoSentence({
          issueId,
          issueStatus,
          author,
          issueCreateTime,
          commentCount,
        })}
      </p>
    </S.IssueInfoBox>
  );
};

export default IssueDetailInfo;
