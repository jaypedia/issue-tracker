import { useRecoilState } from 'recoil';

import * as S from '@/components/common/DropDown/style';
import UserProfile from '@/components/common/UserProfile';
import { FILTER } from '@/constants/constants';
import { issueStatusState } from '@/stores/atoms/issue';
import { getUniformMenus, ListDataType, UniformMenuType } from '@/utils/dropdown';

type DetailsMenuProps = {
  title: string;
  filter: UniformMenuType;
  data: ListDataType;
};

const DetailsMenu = ({ title, filter, data }: DetailsMenuProps) => {
  const [issueStatus, setIssueStatus] = useRecoilState(issueStatusState);
  const handleMenuClick = (name: string) => {
    if (filter === FILTER.author) {
      setIssueStatus({ ...issueStatus, author: name });
    } else if (filter === FILTER.label) {
      const currentLabels = issueStatus.label;
      setIssueStatus({
        ...issueStatus,
        label: [...currentLabels, name],
      });
    } else if (filter === FILTER.milestone) {
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

  type MenuType = {
    id: number;
    name: string;
    backgroundColor?: string;
    image?: string;
  };

  return (
    <S.DetailsMenu menuPosition="right" indicatorType="small">
      <S.DetailsMenuTitleWrapper>
        <S.DetailsMenuTitle indicatorType="small">{title}</S.DetailsMenuTitle>
      </S.DetailsMenuTitleWrapper>
      <ul>
        {menu.map(({ id, name, backgroundColor, image }: MenuType) => (
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
