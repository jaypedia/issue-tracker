package team20.issuetracker.domain.issue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import team20.issuetracker.domain.milestone.Milestone;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
    private String title;
    private String content;

    @Enumerated(value = EnumType.STRING)
    private IssueStatus status;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @OneToMany(mappedBy = "issue")
    private List<IssueAssignee> assignees = new ArrayList<>();

    @OneToMany(mappedBy = "issue")
    private List<IssueComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "issue")
    private List<IssueLabel> labels = new ArrayList<>();
}



// Issue 가 연관관계의 주인이 되면 안된다.
// 그렇다고 Issue (1) : Label (N) 의 양방향은 JPA 스펙 상 지원하지 않기 때문에 권장하지 않는다.
// 즉, 연관관계의 주인 역할을 할 새로운 연결 엔티티가 필요한 시점
//    @OneToMany(mappedBy = "issue")
//    private List<IssueLabel> labels = new ArrayList<>();

// 연관관계 편의 메서드 작성
// 한 번에 여러 개의 Label 이 들어올 수 있으니까 List<Label> ~
//    public void addLabel(List<Label> labels) {
//        for (Label label : labels) {
//        //  this.labels.add(label); (X) -> Issue 는 IssueLabel List 를 가지고 있지 Label 리스트를 가지고 있는게 아니다. 이렇게는 안됨
//        //  Issue 와 Label 이 양방향이 아니라, Issue 와 IssueLabel 이 양방향 관계임. 당연히 안됨
//        //  즉, IssueLabel 에 들어온 Labels 값과 현재 Issue 값을 한번에 셋팅해주면 끝
//            this.labels.add(IssueLabel.of(this, label));
//        }
//    }

// @OneToMany 단방향 매핑은 권장되지 않는 방법
// @ManyToOne 양방향 매핑을 사용해야 하는데, 어떻게 ?

// @OneToMany(fetch = FetchType.LAZY)
// @JoinColumn(name = "label_id")
// private List<Label> labels = new ArrayList<>();
