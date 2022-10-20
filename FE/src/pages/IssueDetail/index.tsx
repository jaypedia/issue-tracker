import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import * as S from './style';

import { deleteIssue } from '@/apis/issueApi';
import Comment from '@/components/Comment';
import CommentForm from '@/components/CommentForm';
import Loading from '@/components/common/Loading';
import IssueDetailHeader from '@/components/IssueDetailHeader';
import SideBar from '@/components/SideBar';
import { useGetIssueDetail } from '@/hooks/useIssue';
import useMovePage from '@/hooks/useMovePage';
import { currentIssueState } from '@/stores/atoms/currentIssue';
import { ColumnWrapper } from '@/styles/common';

const IssueDetail = () => {
  const { id } = useParams();
  const { data, isLoading } = useGetIssueDetail(Number(id));
  const setCurrentIssue = useSetRecoilState(currentIssueState);
  const [goHome] = useMovePage('/');
  const handleDeleteClick = () => {
    deleteIssue(Number(id));
    goHome();
  };

  useEffect(() => {
    if (data) {
      const currentIssueData = {
        title: data.title,
        author: data.author,
        createdAt: data.createdAt,
        image: data.image,
        content: data.content,
      };
      setCurrentIssue(currentIssueData);
    }
  }, [data]);

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
                issueId={data.id}
                issueAuthor={data.author}
                imgUrl={data.image}
                userId={data.author}
                createdAt={data.createdAt}
                description={data.content}
                isIssueContent
              />
              {data.comments.map(({ id: commentId, author, image, content, createdAt }) => (
                <Comment
                  key={commentId}
                  issueId={data.id}
                  commentId={commentId}
                  imgUrl={image}
                  createdAt={createdAt}
                  description={content}
                  userId={author}
                  issueAuthor={data.author === author && author}
                  isIssueContent={false}
                />
              ))}
              <CommentForm usage="comment" isIssueContent={false} />
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
