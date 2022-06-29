import { useRecoilValue } from 'recoil';

import * as S from './style';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import TextArea from '@/components/common/TextArea';
import UserProfile from '@/components/common/UserProfile';
import SideBar from '@/components/SideBar';
import { userState } from '@/stores/atoms/user';

type CommentFormProps = {
  newIssue?: boolean;
};

const CommentForm = ({ newIssue }: CommentFormProps) => {
  const userData = useRecoilValue(userState);
  const btnStyle = {
    size: newIssue ? 'medium' : 'small',
    text: newIssue ? 'Submit new issue' : 'Comment',
  };

  return (
    <S.NewIssueForm>
      <S.FlexWrapper>
        <UserProfile imgUrl={userData?.profileImageUrl} userId={userData?.name} size="large" />
        <S.CommentWrapper>
          {newIssue && (
            <Input name="title" placeholder="Title" inputStyle="medium" title="Title" type="text" />
          )}
          <TextArea name="comment" />
          <Button btnSize={btnStyle.size} btnColor="primary" text={btnStyle.text} type="submit" />
        </S.CommentWrapper>
      </S.FlexWrapper>
      {newIssue && <SideBar />}
    </S.NewIssueForm>
  );
};

export default CommentForm;
