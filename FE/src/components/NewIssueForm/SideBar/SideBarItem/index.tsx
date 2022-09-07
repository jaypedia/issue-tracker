import { useRecoilValue } from 'recoil';

import * as S from './style';

import DropDown from '@/components/common/DropDown';
import { detailsMenuListType } from '@/components/common/DropDown/type';
import Label from '@/components/common/Label';
import ProgressBar from '@/components/common/ProgressBar';
import UserProfile from '@/components/common/UserProfile';
import { sideBarState } from '@/stores/atoms/sideBar';
import { Assignee } from '@/types/issueTypes';
import { ILabel } from '@/types/labelTypes';
import { MilestoneType } from '@/types/milestoneTypes';

type ContentsType = string | Assignee[] | ILabel[] | MilestoneType[];

type SideBarItemProps = {
  title: string;
  defaultContents: string | ContentsType;
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
          title={contents[0].title}
          openIssueCount={contents[0].openIssueCount}
          closedIssueCount={contents[0].closedIssueCount}
        />
      );
    default:
      throw new Error('Invalid title for SideBar');
  }
};

const SideBarItem = ({ title, defaultContents, detailsMenuList, checkType }: SideBarItemProps) => {
  const sideBarContents = useRecoilValue(sideBarState);
  const contents = sideBarContents[title].length > 0 ? sideBarContents[title] : defaultContents;

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
