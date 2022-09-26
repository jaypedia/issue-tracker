/*
----- 기본 멤버 등록-----
*/
INSERT INTO issue.member
    (email, name, oauth_id, profile_image_url, role)
VALUES ('shoy1415@gmail.com', 'geombong', '78953393', 'https://avatars.githubusercontent.com/u/78953393?v=4', 'GUEST');

/*
---- 기본 레이블 생성----
*/
INSERT INTO issue.label
(issue.label.author_id, issue.label.created_at, issue.label.updated_at, issue.label.background_color,
 issue.label.description, issue.label.text_color, issue.label.title)
VALUES ('85419343', LOCALTIME, LOCALTIME, '#a2eeef', 'New features', '#0A071B', '✨ Feature'),
       ('85419343', LOCALTIME, LOCALTIME, '#FEF2C0', 'Styling', '#0A071B', '🎨 Design'),
       ('85419343', LOCALTIME, LOCALTIME, '#23026B', 'The largest unit of work', '#FEFEFE', '🧊 EPIC'),
       ('85419343', LOCALTIME, LOCALTIME, '#f29a4e', 'Code refactoring', '#0A071B', '🔨 Refactor'),
       ('85419343', LOCALTIME, LOCALTIME, '#ccffc4', 'Testing(storybook, jest...)', '#0A071B', '✅ Test'),
       ('85419343', LOCALTIME, LOCALTIME, '#e3dede', 'Development environment settings', '#0A071B', '⚙ Setting'),
       ('85419343', LOCALTIME, LOCALTIME, '#facfcf', 'Web accessibility', '#0A071B', '🥰 Accessibility');

/*
---- 기본 마일스톤 생성----
*/
INSERT INTO issue.milestone
(issue.milestone.author_id, issue.milestone.created_at, issue.milestone.updated_at, issue.milestone.description,
 issue.milestone.due_date, issue.milestone.milestone_status, issue.milestone.title)
VALUES ('78953393', LOCALTIME, LOCALTIME, 'Make API', ADDDATE(LOCALTIME, 7), 'OPEN', '[BE] New Week 1'),
       ('85419343', LOCALTIME, LOCALTIME, 'Common Components', ADDDATE(LOCALTIME, 7), 'OPEN', '[FE] Week 1');


/*
---- 기본 이슈 생성----
*/
INSERT INTO issue.issue
(issue.issue.author_id, issue.issue.created_at, issue.issue.updated_at, issue.issue.content, issue.issue.status,
 issue.issue.title, issue.issue.milestone_id, issue.issue.member_id)
VALUES ('78953393', LOCALTIME, LOCALTIME, '검봉 이슈 내용1', 'OPEN', '검봉 이슈1', 1, 1),
       ('78953393', LOCALTIME, LOCALTIME, '검봉 이슈 내용2', 'OPEN', '검봉 이슈2', 1, 1),
       ('78953393', LOCALTIME, LOCALTIME, '검봉 이슈 내용3', 'CLOSED', '검봉 이슈3', null, 1);

/*
---- 기본 코멘트 생성----
*/
INSERT INTO issue.comment
(issue.comment.author_id, issue.comment.created_at, issue.comment.updated_at, issue.comment.author,
 issue.comment.content, issue.comment.image, issue.comment.issue_id)
VALUES ('78953393', LOCALTIME, LOCALTIME, 'geombong', '안녕하세요 검봉 입니다. 테스트용 댓글 입니다1.',
        'https://avatars.githubusercontent.com/u/78953393?v=4', 1),
       ('78953393', LOCALTIME, LOCALTIME, 'geombong', '안녕하세요 검봉 입니다. 테스트용 댓글 입니다2.',
        'https://avatars.githubusercontent.com/u/78953393?v=4', 2);

/*
---- 기본 담당자 생성----
*/
INSERT INTO issue.assignee
(issue.assignee.author_id, issue.assignee.image, issue.assignee.user_id)
VALUES ('78953393', 'https://avatars.githubusercontent.com/u/78953393?v=4', 'geombong');

/*
---- 기본 이슈 담당자 연결----
*/
INSERT INTO issue.issue_assignee
    (assignee_id, issue_id)
VALUES (1, 1),
       (1, 2),
       (1, 3);

/*
---- 기본 이슈 레이블 연결----
*/
INSERT INTO issue.issue_label
    (issue_id, label_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 4);
