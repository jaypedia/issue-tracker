import { useEffect } from 'react';
import { useRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';

import { useGetIssue } from '@/hooks/useIssue';
import { checkBoxState, isAllCheckedState } from '@/stores/atoms/checkbox';
import { issueStatusState } from '@/stores/atoms/issue';

const useCheckBox = () => {
  const [checkedItems, setCheckedItems] = useRecoilState(checkBoxState);
  const [isAllChecked, setIsAllChecked] = useRecoilState(isAllCheckedState);
  const resetCheckedItems = useResetRecoilState(checkBoxState);
  const issueStatus = useRecoilValue(issueStatusState);
  const { data } = useGetIssue(issueStatus);

  const toggleCheckBox = (id: number) => {
    if (checkedItems.has(id)) {
      setCheckedItems(prevCheckedItems => {
        prevCheckedItems.delete(id);
        return new Set([...prevCheckedItems]);
      });
    } else {
      setCheckedItems(prevCheckedItems => {
        return new Set([...prevCheckedItems, id]);
      });
    }
  };

  const toggleAllCheckBoxes = () => {
    if (isAllChecked) {
      resetCheckedItems();
    } else if (data) {
      setCheckedItems(new Set(data.issues.map(issue => issue.id)));
    }
    setIsAllChecked(prevIsAllChecked => !prevIsAllChecked);
  };

  useEffect(() => {
    if (!data?.issues) return;
    setIsAllChecked(() => {
      return checkedItems.size === data.issues.length;
    });
  }, [checkedItems]);

  return {
    isAllChecked,
    toggleCheckBox,
    checkedItems,
    toggleAllCheckBoxes,
  };
};

export default useCheckBox;
