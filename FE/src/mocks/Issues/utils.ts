import { ISSUE_STATUS, QUERY_KEY } from '@/constants/constants';
import { Assignee, IssueStatusType } from '@/types/issueTypes';

// Reference: thanks to @happyGyu

// TODO: Property 'author' does not exist on type 'IssueItemType'.ts(2339)
const filterByQuery = (queryKey: string, queryValue: string | null, originalIssues) => {
  if (queryValue === null) return originalIssues;

  switch (queryKey) {
    case QUERY_KEY.author:
      return originalIssues.filter(issue => issue.author === queryValue);
    case QUERY_KEY.label:
      return originalIssues.filter(issue => issue.labels.some(label => label.title === queryValue));
    case QUERY_KEY.milestone:
      return originalIssues.filter(issue => issue.mileStoneTitle === queryValue);
    case QUERY_KEY.assignees:
      return originalIssues.filter(issue =>
        issue.assignees.some((assignees: Assignee) => assignees.id === Number(queryValue)),
      );
    default:
      return originalIssues;
  }
};

const filterByStatus = (target: IssueStatusType, originalIssues) => {
  const filtered = originalIssues.filter(issue => issue.issueStatus === target);
  const oppositeStatusCount = originalIssues.length - filtered.length;
  return {
    issues: filtered,
    openIssueCount: target === ISSUE_STATUS.open ? filtered.length : oppositeStatusCount,
    closedIssueCount: target === ISSUE_STATUS.closed ? filtered.length : oppositeStatusCount,
  };
};

export const filterIssues = (queryString: string, issues) => {
  const searchParams = new URLSearchParams(queryString);

  const queryKeyList = [
    QUERY_KEY.author,
    QUERY_KEY.label,
    QUERY_KEY.milestone,
    QUERY_KEY.assignees,
  ];

  const filteredByQueries = queryKeyList.reduce(
    (tempIssues, queryKey) =>
      filterByQuery(queryKey, searchParams.get(queryKey) as string, tempIssues),
    issues,
  );

  const filteredByStatus =
    searchParams.get('params') === ISSUE_STATUS.closed
      ? filterByStatus(ISSUE_STATUS.closed, filteredByQueries)
      : filterByStatus(ISSUE_STATUS.open, filteredByQueries);
  return filteredByStatus;
};
