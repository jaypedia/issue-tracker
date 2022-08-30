import { useNavigate, useParams } from 'react-router-dom';

import * as S from './style';

import { deleteIssue } from '@/apis/issueApi';
import Comment from '@/components/Comment';
import CommentForm from '@/components/CommentForm';
import Loading from '@/components/common/Loading';
import IssueDetailHeader from '@/components/IssueDetailHeader';
import SideBar from '@/components/SideBar';
import { useGetIssueDetail } from '@/hooks/useIssue';
import { ColumnWrapper } from '@/styles/common';

const IssueDetail = () => {
  const { id } = useParams();
  const { data, isLoading } = useGetIssueDetail(Number(id));
  const navigate = useNavigate();
  const handleDeleteClick = () => {
    deleteIssue(Number(id));
    navigate('/');
  };

  return (
    <ColumnWrapper>
      {isLoading || !data ? (
        <Loading />
      ) : (
        <>
          <IssueDetailHeader data={data} />
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
                  issueAuthor={data.author === author ? author : undefined}
                />
              ))}
              <CommentForm usage="comment" />
            </S.CommentsConatiner>
            <SideBar
              assignees={data.assignees}
              labels={data.labels}
              milestone={data.milestones}
              onClick={handleDeleteClick}
            />
          </S.ContentsWrapper>
        </>
      )}
    </ColumnWrapper>
  );
};

export default IssueDetail;
