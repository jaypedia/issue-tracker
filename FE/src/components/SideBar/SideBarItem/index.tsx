import * as S from './style';

import DropDown from '@/components/common/DropDown';
import { detailsMenuListType } from '@/components/common/DropDown/type';
import Label from '@/components/common/Label';
import ProgressBar from '@/components/common/ProgressBar';
import UserProfile from '@/components/common/UserProfile';

type SideBarItemProps = {
  title: string;
  contents: string | object;
  detailsMenuList: detailsMenuListType;
  checkType: 'radio' | 'checkBox';
};

const SideBarContents = ({ title, contents }) => {
  if (typeof contents === 'string') {
    return <S.Contents>{contents}</S.Contents>;
  }

  switch (title) {
    case 'Assignees':
      return contents.map(({ id, userId, image }) => (
        <S.Assignee key={id}>
          <UserProfile userId={userId} imgUrl={image} size="small" />
          {userId}
        </S.Assignee>
      ));
    case 'Labels':
      return (
        <S.LabelContainer>
          {contents.map(({ id, title, backgroundColor, textColor }) => (
            <Label
              key={id}
              size="small"
              backgroundColor={backgroundColor}
              textColor={textColor}
              title={title}
            />
          ))}
        </S.LabelContainer>
      );
    case 'Milestone':
      return (
        <ProgressBar
          size="small"
          openIssueCount={contents.openIssueCount}
          closedIssueCount={contents.closedIssueCount}
        />
      );
    default:
      throw new Error('Invalid title for SideBar');
  }
};

const SideBarItem = ({ title, contents, detailsMenuList, checkType }: SideBarItemProps) => {
  return (
    <S.SideBarItemContainer>
      <DropDown
        indicatorType="setting"
        indicatorTitle={title}
        menuPosition="right"
        detailsMenuList={detailsMenuList}
        hasCheckBox
        checkType={checkType}
      />
      <SideBarContents title={title} contents={contents} />
    </S.SideBarItemContainer>
  );
};
export default SideBarItem;
