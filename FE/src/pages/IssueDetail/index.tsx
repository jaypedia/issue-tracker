import { useParams } from 'react-router-dom';

import * as S from './style';

import Comment from '@/components/Comment';
import CommentForm from '@/components/CommentForm';
import Button from '@/components/common/Button';
import CustomLink from '@/components/common/CustomLink';
import Loading from '@/components/common/Loading';
import IssueDetailInfo from '@/components/IssueDetailInfo';
import SideBar from '@/components/SideBar';
import { useGetIssueDetail } from '@/hooks/useIssue';
import { ColumnWrapper, Heading1, FlexBetween } from '@/styles/common';

const IssueDetail = () => {
  const { id } = useParams();
  const { data, isLoading } = useGetIssueDetail(Number(id));

  // TODO: 제목, 내용 수정 가능하도록 Form으로 변경
  return (
    <ColumnWrapper>
      {isLoading || !data ? (
        <Loading />
      ) : (
        <>
          <S.IssueDetailHeaderWrapper>
            <FlexBetween>
              <Heading1>{data.issueTitle}</Heading1>
              <S.ButtonBox>
                <Button size="small" color="grey" text="Edit" />
                <CustomLink
                  path="/new-issue"
                  component={<Button size="small" color="primary" text="New Issue" />}
                />
              </S.ButtonBox>
            </FlexBetween>
            <IssueDetailInfo
              issueStatus={data.issueStatus}
              author={data.author}
              createdAt={data.createdAt}
              commentCount={data.commentCount}
            />
          </S.IssueDetailHeaderWrapper>
          <S.ContentsWrapper>
            <S.CommentsConatiner>
              <Comment
                issueAuthor={data.author}
                imgUrl={data.image}
                userId={data.author}
                createdAt={data.createdAt}
                description={data.content}
              />
              {data.comments.map(({ id: commentId, author, image, content, createdAt }) => (
                <Comment
                  key={commentId}
                  imgUrl={image}
                  createdAt={createdAt}
                  description={content}
                  userId={author}
                  issueAuthor={data.author === author && author}
                />
              ))}
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
