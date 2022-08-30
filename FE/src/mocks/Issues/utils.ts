// Reference: thanks to @happyGyu
import { ISSUE_STATUS, QUERY_KEY } from '@/constants/constants';
import { IssueStatusType, IssueType } from '@/types/issueTypes';

const filterByQuery = (
  queryKey: string,
  queryValue: string | null,
  originalIssues: IssueType[],
) => {
  if (queryValue === null) return originalIssues;

  switch (queryKey) {
    case QUERY_KEY.author:
      return originalIssues.filter(issue => issue.author === queryValue);
    case QUERY_KEY.label:
      return originalIssues.filter(issue => issue.labels.some(label => label.title === queryValue));
    case QUERY_KEY.milestone:
      return originalIssues.filter(issue =>
        issue.milestones.some(milestone => milestone.title === queryValue),
      );
    case QUERY_KEY.assignee:
      return originalIssues.filter(issue =>
        issue.assignees.some(assignee => assignee.userId === queryValue),
      );
    case QUERY_KEY.title:
      return originalIssues.filter(issue =>
        issue.title.toLowerCase().includes(queryValue.toLowerCase()),
      );
    default:
      return originalIssues;
  }
};

const filterByStatus = (target: IssueStatusType, originalIssues: IssueType[]) => {
  const filtered = originalIssues.filter(issue => issue.issueStatus === target);
  const oppositeStatusCount = originalIssues.length - filtered.length;
  return {
    issues: filtered,
    openIssueCount: target === ISSUE_STATUS.open ? filtered.length : oppositeStatusCount,
    closedIssueCount: target === ISSUE_STATUS.closed ? filtered.length : oppositeStatusCount,
  };
};

export const filterIssues = (queryString: string, issues: IssueType[]) => {
  const searchParams = new URLSearchParams(queryString);
  const queryKeyList = [
    QUERY_KEY.author,
    QUERY_KEY.label,
    QUERY_KEY.milestone,
    QUERY_KEY.assignee,
    QUERY_KEY.title,
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

export const filterOpenIssues = (issues: IssueType[]) => {
  return issues.filter(issue => issue.issueStatus === 'open');
};

export const filterClosedIssues = (issues: IssueType[]) => {
  return issues.filter(issue => issue.issueStatus === 'closed');
};
