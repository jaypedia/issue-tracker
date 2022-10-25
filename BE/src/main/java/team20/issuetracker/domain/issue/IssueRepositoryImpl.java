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

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.MultiValueMap;

import java.util.List;

import javax.persistence.EntityManager;

public class IssueRepositoryImpl implements IssueRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public IssueRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    // 정리
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
                .fetch();

        Long count = countQuery(conditionMap);

        return new PageImpl<>(findAllIssues, pageRequest, count);


//        QIssueLabel issueLabelSub = new QIssueLabel("issueLabelSub");
//        QIssueAssignee issueAssigneeSub = new QIssueAssignee("issueAssigneeSub");
//
//        QLabel labelSub = new QLabel("labelSub");
//        QAssignee assigneeSub = new QAssignee("assignee");
//        QComment commentSub = new QComment("commentSub");
//
//        BooleanBuilder builder = new BooleanBuilder();
//
//        if (hasText(conditionMap.get("is"))) {
//            builder.and(issue.status.eq(IssueStatus.valueOf(conditionMap.get("is").toUpperCase())));
//        }
//
//        if (hasText(conditionMap.get("milestone"))) {
//            builder.and(issue.milestone.title.eq(conditionMap.get("milestone")));
//        }
//
//        if (hasText(conditionMap.get("me"))) {
//            builder.and(issue.member.name.eq(conditionMap.get("me")));
//        }
//
//        if (hasText(conditionMap.get("label"))) {
//            builder.and(issue.id.in(
//                JPAExpressions
//                    .select(issueSub.id)
//                    .from(issueSub)
//                    .innerJoin(issueLabelSub).on(issueLabelSub.issue.id.eq(issueSub.id))
//                    .innerJoin(labelSub).on(issueLabelSub.label.id.eq(labelSub.id))
//                    .where(labelSub.title.eq(conditionMap.get("label")))
//            ));
//        }
//
//        if (hasText(conditionMap.get("assignee"))) {
//            builder.and(issue.id.in(
//                JPAExpressions
//                    .select(issueSub.id)
//                    .from(issueSub)
//                    .innerJoin(issueAssigneeSub).on(issueAssigneeSub.issue.id.eq(issueSub.id))
//                    .innerJoin(assigneeSub).on(issueAssigneeSub.assignee.id.eq(assigneeSub.id))
//                    .where(assigneeSub.userId.eq(conditionMap.get("assignee")))
//            ));
//        }
//
//        if (hasText(conditionMap.get("commented"))) {
//            builder.and(issue.id.in(
//                JPAExpressions
//                    .select(issueSub.id)
//                    .from(issueSub)
//                    .innerJoin(commentSub).on(issueSub.id.eq(commentSub.issue.id))
//                    .where(commentSub.author.eq(conditionMap.get("commented")))
//            ));
//        }
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

    private Long countQuery(MultiValueMap<String, String> conditionMap) {
        Long count = queryFactory.
                select(issue.count())
                .from(issue)
                .leftJoin(issue.member, member)
                .leftJoin(issue.milestone, milestone)
                .where(statusEq(conditionMap))
                .fetchOne();

        count = count == null ? 0L : count;
        return count;
    }
}

//    private BooleanExpression status(String status) {
//        if (!hasText(status)) {
//            return null;
//        }
//
//        return issue.status.eq(IssueStatus.valueOf(status.toUpperCase()));
//    }
//
//    private BooleanExpression milestone(String milestone) {
//        if (!hasText(milestone)) {
//            return null;
//        }
//
//        return issue.milestone.title.eq(milestone);
//    }
//
//    private BooleanExpression member(String me) {
//        if (!hasText(me)) {
//            return null;
//        }
//
//        return member.name.eq(me);
//    }
//
//    private BooleanExpression label(QLabel labelSub, String label) {
//        if (!hasText(label)) {
//            return null;
//        }
//
//        return labelSub.title.eq(label);
//    }
//
//    private BooleanExpression assignee(QAssignee assigneeSub, String assignee) {
//        if (!hasText(assignee)) {
//            return null;
//        }
//
//        return assigneeSub.userId.eq(assignee);
//    }
//
//    private BooleanExpression comment(QComment commentSub, String comment) {
//        if (!hasText(comment)) {
//            return null;
//        }
//
//        return commentSub.author.eq(comment);
//    }
//}

// status(conditionMap.get("is")),
//                milestone(conditionMap.get("milestone")),
//                member(conditionMap.get("me")),
//                issue.id.in(
//                    JPAExpressions
//                        .select(issueSub.id)
//                        .from(issueSub)
//                        .innerJoin(issueLabelSub).on(issueLabelSub.issue.id.eq(issueSub.id))
//                        .innerJoin(labelSub).on(issueLabelSub.label.id.eq(labelSub.id))
//                        .where(labelSub.title.eq(conditionMap.get("label")))
//                        .where(label(labelSub, conditionMap.get("label")))
//                ),
//
//                issue.id.in(
//                    JPAExpressions
//                        .select(issueSub.id)
//                        .from(issueSub)
//                        .innerJoin(issueAssigneeSub).on(issueAssigneeSub.issue.id.eq(issueSub.id))
//                        .innerJoin(assigneeSub).on(issueAssigneeSub.assignee.id.eq(assigneeSub.id))
//                        .where(assigneeSub.userId.eq(conditionMap.get("assignee")))
//                        .where(assignee(assigneeSub, conditionMap.get("assignee")))
//                ),
//
//                issue.id.in(
//                    JPAExpressions
//                        .select(issueSub.id)
//                        .from(issueSub)
//                        .innerJoin(commentSub).on(issueSub.id.eq(commentSub.issue.id))
//                        .where(commentSub.author.eq(conditionMap.get("commented")))
//                        .where(comment(commentSub, conditionMap.get("commented")))
//                )
