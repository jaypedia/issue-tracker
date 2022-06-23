import moment from 'moment';

import CheckBoxIcon from '../CheckBox';
import { IssueItemType } from '../type';
import * as S from './style';

import CustomLink from '@/components/common/CustomLink';
import Label from '@/components/common/Label';
import UserProfile from '@/components/common/UserProfile';
import { Closed } from '@/icons/Closed';
import { Milestone } from '@/icons/Milestone';
import { Open } from '@/icons/Open';
import { COLOR } from '@/styles/color';

const IssueStatusIcon = ({ status }: { status: string }) => {
  switch (status) {
    case 'open':
      return <Open color={COLOR.success[200]} />;
    case 'close':
      return <Closed color={COLOR.primary[200]} />;
    default:
      throw new Error('state is not correct');
  }
};

const IssueInfoSentence = ({ issue }: IssueItemType) => {
  switch (issue.issueStatus) {
    case 'open':
      return `#${issue.id} opened ${moment(issue.issueCreateTime).fromNow()} by ${issue.author}`;
    case 'close':
      return `#${issue.id} by ${issue.author} was closed #${issue.id} ${moment(
        issue.issueCreateTime,
      ).fromNow()} `;
    default:
      throw new Error('Error');
  }
};

const IssueItem = ({ issue }: IssueItemType) => {
  const { issueStatus } = issue;
  return (
    <S.IssueItem>
      <S.Flex>
        <CheckBoxIcon />
        <S.IssueInfoContainer>
          <S.IssueInfo>
            <IssueStatusIcon status={issueStatus} />
            <CustomLink path="/" component={<S.IssueTitle>{issue.issueTitle}</S.IssueTitle>} />
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
            {IssueInfoSentence({ issue })}
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
