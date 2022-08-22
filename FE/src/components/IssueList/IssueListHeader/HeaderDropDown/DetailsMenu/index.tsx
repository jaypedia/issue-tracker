import { useRecoilState } from 'recoil';

import * as S from '@/components/common/DropDown/style';
import UserProfile from '@/components/common/UserProfile';
import { issueStatusState } from '@/stores/atoms/issue';
import { getUniformMenus } from '@/utils/dropdown';

const DetailsMenu = ({ title, filter, data }) => {
  const [issueStatus, setIssueStatus] = useRecoilState(issueStatusState);

  const handleMenuClick = (name: string) => {
    if (filter === 'author') {
      setIssueStatus({ ...issueStatus, author: name });
    } else if (filter === 'label') {
      const currentLabels = issueStatus.label;
      setIssueStatus({
        ...issueStatus,
        label: [...currentLabels, name],
      });
    } else if (filter === 'milestone') {
      setIssueStatus({
        ...issueStatus,
        milestone: name,
      });
    } else {
      setIssueStatus({
        ...issueStatus,
        assignee: name,
      });
    }
  };

  const menu = getUniformMenus(filter, data);

  return (
    <S.DetailsMenu menuPosition="right" indicatorType="small">
      <S.DetailsMenuTitleWrapper>
        <S.DetailsMenuTitle indicatorType="small">{title}</S.DetailsMenuTitle>
      </S.DetailsMenuTitleWrapper>
      <ul>
        {menu.map(({ id, name, backgroundColor, image }) => (
          <S.DetailsMenuItem key={id} indicatorType="small" onClick={() => handleMenuClick(name)}>
            <S.Menu>
              {image && <UserProfile imgUrl={image} userId={name} size="small" />}
              {backgroundColor && <S.LabelColorCircle backgroundColor={backgroundColor} />}
              <p>{name}</p>
            </S.Menu>
          </S.DetailsMenuItem>
        ))}
      </ul>
    </S.DetailsMenu>
  );
};

export default DetailsMenu;
