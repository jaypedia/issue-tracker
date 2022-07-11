package team20.issuetracker.domain.issue;

import team20.issuetracker.domain.assignee.Assignee;
import team20.issuetracker.domain.member.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Enumerated(value = EnumType.STRING)
    private IssueStatus status;

    private LocalDateTime createdAt;

    // TODO : 회원과 이슈 매핑 (이슈 다 : 회원 일)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // TODO : 담당자와 이슈 매핑 (이슈 일 : 담당자 다)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private List<Assignee> assignees = new ArrayList<>();
}
