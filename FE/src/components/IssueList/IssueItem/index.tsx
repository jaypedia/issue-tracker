import { useState, useEffect } from 'react';

import * as S from './style';

import CheckBox from '@/components/common/CheckBox';
import CustomLink from '@/components/common/CustomLink';
import Label from '@/components/common/Label';
import UserProfile from '@/components/common/UserProfile';
import { ISSUE_STATUS } from '@/constants/constants';
import useCheckBox from '@/hooks/useCheckBox';
import ClosedIcon from '@/icons/Closed';
import { Milestone } from '@/icons/Milestone';
import OpenIcon from '@/icons/Open';
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
  const {
    id: issueId,
    issueStatus,
    author,
    createdAt,
    title,
    labels,
    milestones,
    assignees,
  } = issue;
  const [isChecked, setIsChecked] = useState(false);
  const { isAllChecked, toggleCheckBox, checkedItems } = useCheckBox();

  const handleCheck = () => {
    toggleCheckBox(issueId);
    if (isChecked) {
      setIsChecked(false);
    } else {
      setIsChecked(true);
    }
  };

  useEffect(() => {
    if (!isAllChecked && !checkedItems.has(issueId)) {
      setIsChecked(false);
    } else if (checkedItems.has(issueId)) {
      setIsChecked(true);
    }
  }, [issueId, isAllChecked, checkedItems]);

  return (
    <Item>
      <Flex>
        <CheckBox
          id={issueId.toString()}
          isHeader={false}
          onChange={handleCheck}
          checked={isAllChecked || isChecked}
        />
        <S.IssueInfoContainer>
          <S.IssueInfo>
            <IssueStatusIcon status={issueStatus} />
            <CustomLink
              path={`issue/${issueId}`}
              component={<S.IssueTitle>{title}</S.IssueTitle>}
            />
            <S.LabelContainer>
              {labels.length > 0 &&
                labels.map(({ id, title: labelTitle, backgroundColor, textColor }) => (
                  <Label
                    key={id}
                    size="small"
                    title={labelTitle}
                    backgroundColor={backgroundColor}
                    textColor={textColor}
                  />
                ))}
            </S.LabelContainer>
          </S.IssueInfo>
          <S.IssueInfoBottom>
            {getIssueInfoSentence({ issueId, issueStatus, author, createdAt })}
            {milestones.length > 0 && (
              <S.MilestonBox>
                <Milestone />
                {milestones[0].title}
              </S.MilestonBox>
            )}
          </S.IssueInfoBottom>
        </S.IssueInfoContainer>
      </Flex>
      <S.IssueAssignees>
        {assignees.map(({ id, image }) => (
          <UserProfile key={id} imgUrl={image} userId={String(id)} size="small" />
        ))}
      </S.IssueAssignees>
    </Item>
  );
};

export default IssueItem;
