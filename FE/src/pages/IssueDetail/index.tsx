import axios from 'axios';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import * as S from './style';

import Comment from '@/components/Comment';
import CommentForm from '@/components/CommentForm';
import Button from '@/components/common/Button';
import CustomLink from '@/components/common/CustomLink';
import IssueDetailInfo from '@/components/IssueDetailInfo';
import SideBar from '@/components/SideBar';
import { ColumnWrapper, Heading1, FlexBetween } from '@/styles/common';

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

  const { issueId, author, issueCreateTime, issueStatus, commentCount, issueTitle } = issue;

  // TODO: new-issue 버튼 클릭해서 새로운 이슈 페이지 이동 시 path가 누적되는 문제 해결하기
  return (
    <ColumnWrapper>
      {!isLoading && (
        <>
          <S.IssueDetailHeaderWrapper>
            <FlexBetween>
              <Heading1>{issueTitle}</Heading1>
              <S.ButtonBox>
                <Button btnSize="small" btnColor="grey" text="Edit" />
                <CustomLink
                  path="new-issue"
                  component={<Button btnSize="small" btnColor="primary" text="New Issue" />}
                />
              </S.ButtonBox>
            </FlexBetween>
            <IssueDetailInfo
              issueId={issueId}
              issueStatus={issueStatus}
              author={author}
              issueCreateTime={issueCreateTime}
              commentCount={commentCount}
            />
          </S.IssueDetailHeaderWrapper>
          <S.ContentsWrapper>
            <S.CommentsConatiner>
              <Comment
                issueAuthor={author}
                imgUrl="https://avatars.githubusercontent.com/u/85419343?s=80&v=4"
                userId={author}
                createTime={issueCreateTime}
                description=""
              />
              <CommentForm />
            </S.CommentsConatiner>
            <SideBar />
          </S.ContentsWrapper>
        </>
      )}
    </ColumnWrapper>
  );
};

export default IssueDetail;
