import * as S from './style';

import Label from '@/components/common/Label';
import { ISSUE_STATUS } from '@/constants/constants';
import ClosedIcon from '@/icons/Closed';
import OpenIcon from '@/icons/Open';
import { COLOR } from '@/styles/color';
import { IssueStatusType } from '@/types/issueTypes';
import { convertFirstLetterToUppercase, getIssueDetailInfoSentence } from '@/utils/issue';

type IssueDetailInfoProps = {
  issueStatus: IssueStatusType;
  author: string;
  createdAt: string;
  commentCount: number;
};

const IssueDetailInfo = ({
  issueStatus,
  author,
  createdAt,
  commentCount,
}: IssueDetailInfoProps) => {
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
        {getIssueDetailInfoSentence({
          author,
          createdAt,
          commentCount,
        })}
      </p>
    </S.IssueInfoBox>
  );
};

export default IssueDetailInfo;
