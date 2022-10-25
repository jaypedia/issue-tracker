package team20.issuetracker.domain.issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.MultiValueMap;

public interface IssueRepositoryCustom {

    Page<Issue> findAllIssuesByCondition(MultiValueMap<String, String> conditionMap, PageRequest pageRequest);
}
