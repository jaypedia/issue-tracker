import { IssueItemType } from '../type';
import * as S from './style';

import CheckBox from '@/components/common/CheckBox';
import CustomLink from '@/components/common/CustomLink';
import Label from '@/components/common/Label';
import UserProfile from '@/components/common/UserProfile';
import { ISSUE_STATUS } from '@/constants/constants';
import ClosedIcon from '@/icons/Closed';
import { Milestone } from '@/icons/Milestone';
import OpenIcon from '@/icons/Open';
import { COLOR } from '@/styles/color';
import { getIssueInfoSentence } from '@/utils/issue';

const IssueStatusIcon = ({ status }: { status: string }) => {
  switch (status) {
    case ISSUE_STATUS.open:
      return <OpenIcon color={COLOR.success[200]} />;
    case ISSUE_STATUS.closed:
      return <ClosedIcon color={COLOR.primary[200]} />;
    default:
      throw new Error('Status is not correct');
  }
};

const IssueItem = ({ issue }: IssueItemType) => {
  const { id: issueId, issueStatus, author, issueCreateTime } = issue;
  return (
    <S.IssueItem>
      <S.Flex>
        <CheckBox id={issue.id.toString()} isHeader={false} />
        <S.IssueInfoContainer>
          <S.IssueInfo>
            <IssueStatusIcon status={issueStatus} />
            <CustomLink
              path={`issue/${issueId}`}
              component={<S.IssueTitle>{issue.issueTitle}</S.IssueTitle>}
            />
            <S.LabelContainer>
              {issue.labels.map(({ id, title, backgroundColor, textColor }) => (
                <Label
                  key={id}
                  size="small"
                  title={title}
                  backgroundColor={backgroundColor}
                  textColor={textColor}
                />
              ))}
            </S.LabelContainer>
          </S.IssueInfo>
          <S.IssueInfoBottom>
            {getIssueInfoSentence({ issueId, issueStatus, author, issueCreateTime })}
            <S.MilestonBox>
              <Milestone />
              {issue.mileStoneTitle}
            </S.MilestonBox>
          </S.IssueInfoBottom>
        </S.IssueInfoContainer>
      </S.Flex>
      <S.IssueAssignees>
        {issue.assignees.map(({ id, image }) => (
          <UserProfile key={id} imgUrl={image} userId={String(id)} size="small" />
        ))}
      </S.IssueAssignees>
    </S.IssueItem>
  );
};

export default IssueItem;
