import ReactPaginate from 'react-paginate';
import { useRecoilState } from 'recoil';
import styled from 'styled-components';

import IssueContainer from './IssueContainer';
import IssueListHeader from './IssueListHeader';

import Loading from '@/components/common/Loading';
import { useGetIssue } from '@/hooks/useIssue';
import { issueStatusState } from '@/stores/atoms/issue';
import { COLOR } from '@/styles/color';
import { ListContainer } from '@/styles/list';

const Pagination = styled(ReactPaginate)`
  display: flex;
  justify-content: center;
  color: ${COLOR.grey[400]};

  & > * {
    padding: 10px;

    :hover {
      border: 1px solid ${COLOR.grey[200]};
      border-radius: 10px;
    }
  }

  .currentPage {
    color: white;
    background-color: ${COLOR.primary[300]};
    border-radius: 10px;
  }
`;

const IssueList = () => {
  const [issueStatus, setIssueStatus] = useRecoilState(issueStatusState);
  const { data, isLoading } = useGetIssue(issueStatus);

  const handlePageClick = (e: { selected: number }) => {
    const page = e.selected + 1;
    setIssueStatus({ ...issueStatus, page });
  };

  return isLoading || !data ? (
    <Loading />
  ) : (
    <>
      <ListContainer>
        <IssueListHeader
          openIssueCount={data.openIssueCount}
          closedIssueCount={data.closedIssueCount}
        />
        <IssueContainer issues={data.issues} />
      </ListContainer>
      <Pagination
        pageCount={data.pageable.totalPages}
        pageRangeDisplayed={10}
        breakLabel="..."
        previousLabel="<"
        nextLabel=">"
        onPageChange={handlePageClick}
        activeClassName="currentPage"
        forcePage={issueStatus.page - 1}
      />
    </>
  );
};

export default IssueList;
