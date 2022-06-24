import axios from 'axios';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import * as S from './style';

import CommentForm from '@/components/CommentForm';
import Button from '@/components/common/Button';
import Label from '@/components/common/Label';
import SideBar from '@/components/SideBar';
import { COLOR } from '@/styles/color';
import { ColumnWrapper, Heading1, FlexBetween, FlexColumn } from '@/styles/common';
import { convertFirstLetterToUppercase, getIssueInfoSentence } from '@/utils/issue';

const IssueDetail = () => {
  const { id } = useParams();

  const [issue, setIssue] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const getIssue = async () => {
    setIsLoading(true);
    const response = await axios.get(`/api/issues/${id}`);
    if (response.data) {
      setIssue(response.data);
    }
    setIsLoading(false);
  };

  useEffect(() => {
    getIssue();
  }, []);

  return (
    <ColumnWrapper>
      {!isLoading && (
        <>
          <S.IssueDetailHeaderWrapper>
            <FlexBetween>
              <Heading1>{issue.issueTitle}</Heading1>
              <S.ButtonBox>
                <Button btnSize="small" btnColor="grey" text="Edit" />
                <Button btnSize="small" btnColor="primary" text="New Issue" />
              </S.ButtonBox>
            </FlexBetween>
            <S.IssueInfoBox>
              <Label
                title={convertFirstLetterToUppercase(issue.issueStatus)}
                size="large"
                backgroundColor={COLOR.success[300]}
                textColor={COLOR.white}
              />
              {getIssueInfoSentence({
                issueId: issue.id,
                issueStatus: issue.issueStatus,
                author: issue.author,
                issueCreateTime: issue.issueCreateTime,
                commentCount: issue.commentCount,
              })}
            </S.IssueInfoBox>
          </S.IssueDetailHeaderWrapper>
          <S.ContentsWrapper>
            <FlexColumn>
              <div>CommentBox</div>
              <div>CommentBox</div>
              <div>CommentBox</div>
              <CommentForm />
            </FlexColumn>
            <SideBar />
          </S.ContentsWrapper>
        </>
      )}
    </ColumnWrapper>
  );
};

export default IssueDetail;
