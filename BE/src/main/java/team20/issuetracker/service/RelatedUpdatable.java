package team20.issuetracker.service;

import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.service.dto.request.RequestUpdateIssueRelatedDto;

public interface RelatedUpdatable {
    void updateRelatedType(RequestUpdateIssueRelatedDto requestUpdateIssueRelatedDto, UpdateType updateType, Issue findIssue);
}