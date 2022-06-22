import * as S from './style';

import Button from '@/components/common/Button';
import ContentsHeader from '@/components/common/ContentsHeader';
import Input from '@/components/common/Input';
import TextArea from '@/components/common/TextArea';
import UserProfile from '@/components/common/UserProfile';
import SideBar from '@/components/SideBar';
import { InnerContainer, MainWrapper } from '@/styles/common';

const NewIssueContents = () => {
  return (
    <S.FlexWrapper>
      <UserProfile
        imgUrl="https://avatars.githubusercontent.com/u/85419343?s=80&v=4"
        userId="jaypeida"
        size="large"
      />
      <S.NewIssueContentsWrapper>
        <Input name="title" placeholder="Title" inputStyle="medium" title="Title" type="text" />
        <TextArea name="comment" />
        <Button btnSize="medium" btnColor="primary" text="Submit new issue" type="submit" />
      </S.NewIssueContentsWrapper>
    </S.FlexWrapper>
  );
};

const NewIssue = () => {
  return (
    <MainWrapper>
      <InnerContainer>
        <S.ColumnWrapper>
          <ContentsHeader headingText="Create a new issue" />
          <S.NewIssueForm>
            <NewIssueContents />
            <SideBar />
          </S.NewIssueForm>
        </S.ColumnWrapper>
      </InnerContainer>
    </MainWrapper>
  );
};

export default NewIssue;
