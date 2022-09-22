import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue, useRecoilState } from 'recoil';

import SideBar from './SideBar';
import * as S from './style';

import { postIssue } from '@/apis/issueApi';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import TextArea from '@/components/common/TextArea';
import UserProfile from '@/components/common/UserProfile';
import useButtonDisable from '@/hooks/useButtonDisable';
import { sideBarState, sideBarInitialState } from '@/stores/atoms/sideBar';
import { userState } from '@/stores/atoms/user';

const NewIssueForm = () => {
  const userData = useRecoilValue(userState);
  const titleRef = useRef<HTMLInputElement>(null);
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const navigate = useNavigate();
  const { isButtonDisabled, handleInputChange } = useButtonDisable();
  const [sideBar, setSideBar] = useRecoilState(sideBarState);

  const handleSubmit = () => {
    if (!titleRef.current || !commentRef.current) return;

    const issueData = {
      title: titleRef.current.value,
      content: commentRef.current.value,
      assigneeIds: sideBar.Assignees.map(v => v.id),
      labelIds: sideBar.Labels.map(v => v.id),
      milestoneIds: sideBar.Milestone.map(v => v.id),
    };

    postIssue(issueData);
    setSideBar(sideBarInitialState);
    navigate('/');
  };

  const handleCancel = () => {
    navigate(-1);
    setSideBar(sideBarInitialState);
  };

  return (
    <S.NewIssueForm>
      <S.FlexWrapper>
        <UserProfile imgUrl={userData?.profileImageUrl} userId={userData?.name} size="large" />
        <S.CommentWrapper>
          <Input
            name="title"
            placeholder="Title"
            inputStyle="medium"
            title="Title"
            type="text"
            ref={titleRef}
            onChange={handleInputChange}
          />
          <TextArea name="comment" usage="comment" ref={commentRef} />
          <S.ButtonWrapper>
            <Button size="medium" color="grey" text="Cancel" type="button" onClick={handleCancel} />
            <Button
              size="medium"
              color="primary"
              text="Submit new issue"
              type="button"
              onClick={handleSubmit}
              disabled={isButtonDisabled}
            />
          </S.ButtonWrapper>
        </S.CommentWrapper>
      </S.FlexWrapper>
      <SideBar />
    </S.NewIssueForm>
  );
};

export default NewIssueForm;
