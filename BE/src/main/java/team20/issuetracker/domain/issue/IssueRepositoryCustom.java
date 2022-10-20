package team20.issuetracker.domain.issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

public interface IssueRepositoryCustom {

    Page<Issue> findAllIssuesByCondition(Map<String, String> conditionMap, PageRequest pageRequest);
}
