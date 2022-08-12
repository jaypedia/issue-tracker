import IssueTitleForm from './IssueTitleForm';
import * as S from './style';

import Button from '@/components/common/Button';
import CustomLink from '@/components/common/CustomLink';
import IssueDetailInfo from '@/components/IssueDetailInfo';
import useBoolean from '@/hooks/useBoolean';
import { Heading1, FlexBetween } from '@/styles/common';

const IssueDetailHeader = ({ data }) => {
  console.log(data);

  const { id, issueTitle, issueStatus, author, createdAt, commentCount } = data;
  const { booleanState: isEditOpen, setTrue, setFalse } = useBoolean(false);

  const handleEditClick = () => {
    setTrue();
  };

  return (
    <S.IssueDetailHeaderWrapper>
      {isEditOpen ? (
        <IssueTitleForm id={id} title={issueTitle} onCancle={setFalse} />
      ) : (
        <FlexBetween>
          <Heading1>{issueTitle}</Heading1>
          <S.ButtonBox>
            <Button size="small" color="grey" text="Edit" onClick={handleEditClick} />
            <CustomLink
              path="/new-issue"
              component={<Button size="small" color="primary" text="New Issue" />}
            />
          </S.ButtonBox>
        </FlexBetween>
      )}

      <IssueDetailInfo
        issueStatus={issueStatus}
        author={author}
        createdAt={createdAt}
        commentCount={commentCount}
      />
    </S.IssueDetailHeaderWrapper>
  );
};

export default IssueDetailHeader;
