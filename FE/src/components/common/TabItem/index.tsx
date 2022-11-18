import * as S from './style';

import ClosedIcon from '@/icons/Closed';
import { Milestone } from '@/icons/Milestone';
import OpenIcon from '@/icons/Open';

type TabItemType = {
  isOpen?: boolean;
  count?: number;
  isCurrentTab: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  usage?: 'issue' | 'milestone';
};

const TabItem = ({ isOpen, count, onClick, isCurrentTab, usage = 'issue' }: TabItemType) => {
  return (
    <S.TabItem type="button" onClick={onClick} isCurrentTab={isCurrentTab}>
      {isOpen ? (
        <>
          {usage === 'issue' ? <OpenIcon /> : <Milestone />}
          {count} Open
        </>
      ) : (
        <>
          <ClosedIcon />
          {count} Closed
        </>
      )}
    </S.TabItem>
  );
};

export default TabItem;
