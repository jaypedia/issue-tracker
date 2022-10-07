drop table if exists `comment`;
drop table if exists issue_assignee;
drop table if exists issue_label;
drop table if exists issue;
drop table if exists milestone;
drop table if exists assignee;
drop table if exists label;
drop table if exists `member`;


create table assignee
(
    id        bigint not null auto_increment,
    author_id varchar(255),
    image     varchar(255),
    user_id     varchar(255),
    primary key (id)
);

create table `comment`
(
    id         bigint       not null auto_increment,
    author_id  varchar(255) not null,
    created_at date         not null,
    updated_at date         not null,
    author     varchar(255),
    content    longtext,
    image      varchar(255),
    issue_id   bigint,
    primary key (id)
);


create table issue
(
    id           bigint       not null auto_increment,
    author_id    varchar(255) not null,
    created_at   date         not null,
    updated_at   date         not null,
    content      varchar(10000),
    `status`     varchar(255),
    title        varchar(255),
    milestone_id bigint,
    member_id    bigint,
    primary key (id)
);


create table issue_assignee
(
    id          bigint not null auto_increment,
    assignee_id bigint,
    issue_id    bigint,
    primary key (id)
);


create table issue_label
(
    id       bigint not null auto_increment,
    issue_id bigint,
    label_id bigint,
    primary key (id)
);


create table label
(
    id               bigint                                                       not null auto_increment,
    author_id        varchar(255)                                                 not null,
    created_at       date                                                         not null,
    updated_at       date                                                         not null,
    background_color varchar(7)                                                   not null,
    `description`    varchar(100),
    text_color       varchar(7)                                                   not null,
    title            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null,
    primary key (id)
);


create table `member`
(
    id                bigint not null auto_increment,
    email             varchar(255),
    `name`            varchar(255),
    oauth_id          varchar(255),
    profile_image_url varchar(255),
    `role`            varchar(255),
    primary key (id)
);


create table milestone
(
    id               bigint       not null auto_increment,
    author_id        varchar(255) not null,
    created_at       date         not null,
    updated_at       date         not null,
    `description`    varchar(800),
    due_date         date         not null,
    milestone_status varchar(255),
    title            varchar(50)  not null,
    primary key (id)
);


alter table `comment`
    add constraint commentIssueFK
        foreign key (issue_id)
            references issue (id);


alter table issue
    add constraint issueMilestoneFK
        foreign key (milestone_id)
            references milestone (id);

alter table issue
    add constraint issueMemberFK
        foreign key (member_id)
            references `member` (id);


alter table issue_assignee
    add constraint issueAssigneeAssigneeFK
        foreign key (assignee_id)
            references assignee (id);


alter table issue_assignee
    add constraint issueAssigneeIssueFK
        foreign key (issue_id)
            references issue (id);


alter table issue_label
    add constraint issueLabelIssueFK
        foreign key (issue_id)
            references issue (id);


alter table issue_label
    add constraint issueLabelLabelFK
        foreign key (label_id)
            references label (id);
