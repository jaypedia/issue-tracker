package team20.issuetracker.domain.issue;

import team20.issuetracker.domain.member.Member;

import java.time.LocalDateTime;

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
}
