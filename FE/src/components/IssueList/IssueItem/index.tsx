import React from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import * as S from './style';

import CheckBox from '@/components/common/CheckBox';
import CustomLink from '@/components/common/CustomLink';
import Label from '@/components/common/Label';
import UserProfile from '@/components/common/UserProfile';
import { ISSUE_STATUS } from '@/constants/constants';
import ClosedIcon from '@/icons/Closed';
import { Milestone } from '@/icons/Milestone';
import OpenIcon from '@/icons/Open';
import { checkBoxState } from '@/stores/atoms/checkbox';
import { COLOR } from '@/styles/color';
import { Flex } from '@/styles/common';
import { Item } from '@/styles/list';
import { IssueType } from '@/types/issueTypes';
import { getIssueInfoSentence } from '@/utils/issue';

const IssueStatusIcon = ({ status }: { status: string }) => {
  switch (status) {
    case ISSUE_STATUS.open:
      return <OpenIcon color={COLOR.success[400]} />;
    case ISSUE_STATUS.closed:
      return <ClosedIcon color={COLOR.primary[200]} />;
    default:
      throw new Error('Status is not correct');
  }
};

const IssueItem = ({ issue }: { issue: IssueType }) => {
  const { id: issueId, issueStatus, author, createdAt } = issue;
  const checkedItems = useRecoilValue(checkBoxState);
  const setCheckedItems = useSetRecoilState(checkBoxState);

  const handleCheck = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.checked) {
      checkedItems.add(issue.id);
      setCheckedItems(checkedItems);
    } else if (!e.target.checked && checkedItems.has(issue.id)) {
      checkedItems.delete(issue.id);
      setCheckedItems(checkedItems);
    }
  };

  return (
    <Item>
      <Flex>
        <CheckBox id={issue.id.toString()} isHeader={false} onChange={handleCheck} />
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
            {getIssueInfoSentence({ issueId, issueStatus, author, createdAt })}
            <S.MilestonBox>
              <Milestone />
              {issue.milestone.title}
            </S.MilestonBox>
          </S.IssueInfoBottom>
        </S.IssueInfoContainer>
      </Flex>
      <S.IssueAssignees>
        {issue.assignees.map(({ id, image }) => (
          <UserProfile key={id} imgUrl={image} userId={String(id)} size="small" />
        ))}
      </S.IssueAssignees>
    </Item>
  );
};

export default IssueItem;
