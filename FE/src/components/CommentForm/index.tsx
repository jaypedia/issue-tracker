import { useRef } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import * as S from './style';

import { patchIssue } from '@/apis/issueApi';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import TextArea from '@/components/common/TextArea';
import UserProfile from '@/components/common/UserProfile';
import SideBar from '@/components/SideBar';
import { COMMENT_FORM_TYPE } from '@/constants/constants';
import { useRefetchIssueDetail } from '@/hooks/useIssue';
import { userState } from '@/stores/atoms/user';
import { IssueType } from '@/types/issueTypes';

type CommentFormProps = {
  usage: 'edit' | 'newIssue' | 'comment';
  onSubmit?: (issueData: IssueType, id?: number) => Promise<void>;
  onCancel?: () => void;
  content?: string;
  author?: string;
  image?: string;
};

const CommentForm = ({ usage, onSubmit, onCancel, content, author, image }: CommentFormProps) => {
  const { id } = useParams();

  const userData = useRecoilValue(userState);
  const titleRef = useRef<HTMLInputElement>(null);
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const buttonStyleObj = {
    edit: { size: 'small', text: 'Update comment' },
    newIssue: { size: 'medium', text: 'Submit new issue' },
    comment: { size: 'small', text: 'Comment' },
  } as const;
  const navigate = useNavigate();
  const { mutate } = useRefetchIssueDetail(Number(id));

  // TODO: commentCount & comment should be sent?
  // TODO: never type
  const handleSubmit = () => {
    const issueData = {
      author: author || 'Millie', // 전역
      image: image || 'https://avatars.githubusercontent.com/u/68211156?s=40&v=4', // 전역
      createdAt: new Date().toString(),
      issueTitle: titleRef.current?.value,
      content: commentRef.current?.value,
      assignees: [],
      milestone: [],
      labels: [],
      comments: [],
      commentCount: 0,
      issueStatus: 'open',
    };

    if (onSubmit) {
      onSubmit(issueData);
      navigate('/');
    } else if (onCancel) {
      patchIssue(Number(id), issueData);
      onCancel();
      mutate();
    }
  };

  return (
    <S.NewIssueForm>
      <S.FlexWrapper>
        {usage !== COMMENT_FORM_TYPE.edit && (
          <UserProfile imgUrl={userData?.profileImageUrl} userId={userData?.name} size="large" />
        )}
        <S.CommentWrapper>
          {usage === COMMENT_FORM_TYPE.newIssue && (
            <Input
              name="title"
              placeholder="Title"
              inputStyle="medium"
              title="Title"
              type="text"
              ref={titleRef}
            />
          )}
          <TextArea name="comment" usage="comment" ref={commentRef} defaultValue={content} />
          <S.ButtonWrapper>
            {usage === COMMENT_FORM_TYPE.edit && (
              <Button size="small" color="grey" text="Cancel" type="button" onClick={onCancel} />
            )}
            <Button
              size={buttonStyleObj[usage].size}
              color="primary"
              text={buttonStyleObj[usage].text}
              type="button"
              onClick={handleSubmit}
            />
          </S.ButtonWrapper>
        </S.CommentWrapper>
      </S.FlexWrapper>
      {usage === COMMENT_FORM_TYPE.newIssue && <SideBar />}
    </S.NewIssueForm>
  );
};

export default CommentForm;
