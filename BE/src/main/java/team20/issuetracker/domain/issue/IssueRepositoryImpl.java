package team20.issuetracker.domain.issue;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import team20.issuetracker.domain.assginee.QAssignee;
import team20.issuetracker.domain.comment.QComment;
import team20.issuetracker.domain.label.QLabel;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.hasText;
import static team20.issuetracker.domain.issue.QIssue.*;
import static team20.issuetracker.domain.member.QMember.*;
import static team20.issuetracker.domain.milestone.QMilestone.*;

public class IssueRepositoryImpl implements IssueRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public IssueRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    // 정리
    @Override
    public Page<Issue> findAllIssuesByCondition(Map<String, String> conditionMap, PageRequest pageRequest) {
        QIssue issueSub = new QIssue("issueSub");
        QIssueLabel issueLabelSub = new QIssueLabel("issueLabelSub");
        QIssueAssignee issueAssigneeSub = new QIssueAssignee("issueAssigneeSub");

        QLabel labelSub = new QLabel("labelSub");
        QAssignee assigneeSub = new QAssignee("assignee");
        QComment commentSub = new QComment("commentSub");

        BooleanBuilder builder = new BooleanBuilder();

        if (hasText(conditionMap.get("is"))) {
            builder.and(issue.status.eq(IssueStatus.valueOf(conditionMap.get("is").toUpperCase())));
        }

        if (hasText(conditionMap.get("milestone"))) {
            builder.and(issue.milestone.title.eq(conditionMap.get("milestone")));
        }

        if (hasText(conditionMap.get("me"))) {
            builder.and(issue.member.name.eq(conditionMap.get("me")));
        }

        if (hasText(conditionMap.get("label"))) {
            builder.and(issue.id.in(
                JPAExpressions
                    .select(issueSub.id)
                    .from(issueSub)
                    .innerJoin(issueLabelSub).on(issueLabelSub.issue.id.eq(issueSub.id))
                    .innerJoin(labelSub).on(issueLabelSub.label.id.eq(labelSub.id))
                    .where(labelSub.title.eq(conditionMap.get("label")))
            ));
        }

        if (hasText(conditionMap.get("assignee"))) {
            builder.and(issue.id.in(
                JPAExpressions
                    .select(issueSub.id)
                    .from(issueSub)
                    .innerJoin(issueAssigneeSub).on(issueAssigneeSub.issue.id.eq(issueSub.id))
                    .innerJoin(assigneeSub).on(issueAssigneeSub.assignee.id.eq(assigneeSub.id))
                    .where(assigneeSub.userId.eq(conditionMap.get("assignee")))
            ));
        }

        if (hasText(conditionMap.get("commented"))) {
            builder.and(issue.id.in(
                JPAExpressions
                    .select(issueSub.id)
                    .from(issueSub)
                    .innerJoin(commentSub).on(issueSub.id.eq(commentSub.issue.id))
                    .where(commentSub.author.eq(conditionMap.get("commented")))
            ));
        }


        List<Issue> findAllIssues = queryFactory
            .select(issue)
            .from(issue)
            .leftJoin(issue.member, member)
            .fetchJoin()
            .leftJoin(issue.milestone, milestone)
            .fetchJoin()

            .where(builder)
            .offset(pageRequest.getOffset())
            .limit(pageRequest.getPageSize())
            .fetch();

        return new PageImpl<>(findAllIssues);
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
