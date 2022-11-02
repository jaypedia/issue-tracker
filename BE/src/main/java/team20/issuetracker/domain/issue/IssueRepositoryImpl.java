package team20.issuetracker.domain.issue;

import static com.querydsl.jpa.JPAExpressions.select;
import static team20.issuetracker.domain.assginee.QAssignee.assignee;
import static team20.issuetracker.domain.comment.QComment.comment;
import static team20.issuetracker.domain.issue.QIssue.issue;
import static team20.issuetracker.domain.issue.QIssueAssignee.issueAssignee;
import static team20.issuetracker.domain.issue.QIssueLabel.issueLabel;
import static team20.issuetracker.domain.label.QLabel.label;
import static team20.issuetracker.domain.member.QMember.member;
import static team20.issuetracker.domain.milestone.QMilestone.milestone;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.MultiValueMap;

import java.util.List;

import javax.persistence.EntityManager;

public class IssueRepositoryImpl implements IssueRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public IssueRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Issue> findAllIssuesByCondition(MultiValueMap<String, String> conditionMap, PageRequest pageRequest) {
        QIssue issueSub = new QIssue("issueSub");

        List<Issue> findAllIssues = queryFactory
                .select(issue)
                .from(issue)
                .leftJoin(issue.member, member)
                .fetchJoin()
                .leftJoin(issue.milestone, milestone)
                .fetchJoin()
                .where(
                        statusEq(conditionMap),
                        milestoneEq(conditionMap),
                        authorEq(conditionMap),
                        labelEq(conditionMap, issueSub),
                        assigneeEq(conditionMap, issueSub),
                        commentedEq(conditionMap, issueSub)
                )
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(sort(pageRequest))
                .fetch();

        Long count = countQueryByIssueStatus(conditionMap);

        return new PageImpl<>(findAllIssues, pageRequest, count);
    }

    @Override
    public Long countQueryByIssueStatus(MultiValueMap<String, String> conditionMap) {
        QIssue issueSub = new QIssue("issueSub");
        Long count = queryFactory.
            select(issue.count())
            .from(issue)
            .leftJoin(issue.member, member)
            .leftJoin(issue.milestone, milestone)
            .where(
                statusEq(conditionMap),
                milestoneEq(conditionMap),
                authorEq(conditionMap),
                labelEq(conditionMap, issueSub),
                assigneeEq(conditionMap, issueSub),
                commentedEq(conditionMap, issueSub)
            )
            .fetchOne();

        count = count == null ? 0L : count;
        return count;
    }

    @Override
    public Long allIssueCountQuery(MultiValueMap<String, String> conditionMap) {
        QIssue issueSub = new QIssue("issueSub");
        Long count = queryFactory.
            select(issue.count())
            .from(issue)
            .leftJoin(issue.member, member)
            .leftJoin(issue.milestone, milestone)
            .where(
                milestoneEq(conditionMap),
                authorEq(conditionMap),
                labelEq(conditionMap, issueSub),
                assigneeEq(conditionMap, issueSub),
                commentedEq(conditionMap, issueSub)
            )
            .fetchOne();

        count = count == null ? 0L : count;
        return count;
    }

    private BooleanExpression statusEq(MultiValueMap<String, String> conditionMap) {
        return conditionMap.get("is") != null ? issue.status.eq(
                IssueStatus.valueOf(conditionMap.get("is").get(0).toUpperCase())
        ) : null;
    }

    private BooleanExpression milestoneEq(MultiValueMap<String, String> conditionMap) {
        return conditionMap.get("milestone") != null ? issue.milestone.title.eq(
                conditionMap.get("milestone").get(0)
        ) : null;
    }

    private BooleanExpression authorEq(MultiValueMap<String, String> conditionMap) {
        return conditionMap.get("author") != null ? member.name.eq(
                conditionMap.get("author").get(0)
        ) : null;
    }

    private BooleanExpression labelEq(MultiValueMap<String, String> conditionMap, QIssue issueSub) {
        return conditionMap.get("label") != null ? issue.id.in(
                select(issueSub.id)
                        .from(issueSub)
                        .innerJoin(issueLabel).on(issueLabel.issue.id.eq(issueSub.id))
                        .innerJoin(label).on(issueLabel.label.id.eq(label.id))
                        .where(label.title.in(conditionMap.get("label")))
        ) : null;
    }

    private BooleanExpression assigneeEq(MultiValueMap<String, String> conditionMap, QIssue issueSub) {
        return conditionMap.get("assignee") != null ? issue.id.in(
                select(issueSub.id)
                        .from(issueSub)
                        .innerJoin(issueAssignee).on(issueAssignee.issue.id.eq(issueSub.id))
                        .innerJoin(assignee).on(issueAssignee.assignee.id.eq(assignee.id))
                        .where(assignee.userId.in(conditionMap.get("assignee")))
        ) : null;
    }

    private BooleanExpression commentedEq(MultiValueMap<String, String> conditionMap, QIssue issueSub) {
        return conditionMap.get("commented") != null ? issue.id.in(
                select(issueSub.id)
                        .from(issueSub)
                        .innerJoin(comment).on(issueSub.id.eq(comment.issue.id))
                        .where(comment.author.in(conditionMap.get("commented")))
        ) : null;
    }

    private OrderSpecifier<?> sort(PageRequest pageRequest) {
        if (!pageRequest.getSort().isEmpty()) {
            for (Sort.Order order : pageRequest.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                if (order.getProperty().equals("id")) {
                    return new OrderSpecifier<>(direction, issue.id);
                }
            }
        }

        return null;
    }
}
