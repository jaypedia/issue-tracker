/*
---- 기본 레이블 생성----
*/
INSERT INTO issue.label
(author_id, created_at, background_color, `description`, text_color, title)
VALUES ('85419343', '2022-08-08', '#a2eeef', 'New features', '#0A071B', '✨ Feature'),
       ('85419343', '2022-08-08', '#FEF2C0', 'Styling', '#0A071B', '🎨 Design'),
       ('85419343', '2022-08-08', '#23026B', 'The largest unit of work', '#FEFEFE', '🧊 EPIC'),
       ('85419343', '2022-08-08', '#f29a4e', 'Code refactoring', '#0A071B', '🔨 Refactor'),
       ('85419343', '2022-08-08', '#ccffc4', 'Testing(storybook, jest...)', '#0A071B', '✅ Test'),
       ('85419343', '2022-08-08', '#e3dede', 'Development environment settings', '#0A071B', '⚙ Setting'),
       ('85419343', '2022-08-08', '#facfcf', 'Web accessibility', '#0A071B', '🥰 Accessibility');

/*
---- 기본 마일스톤 생성----
*/
INSERT INTO issue.milestone
(author_id, created_at, `description`, deu_date, milestone_status, start_date, title)
VALUES ('78953393', LOCALTIME, 'Make API', ADDDATE(LOCALTIME, 7), 'OPEN', LOCALTIME, '[BE] New Week 1'),
       ('85419343', LOCALTIME, 'Common Components', ADDDATE(LOCALTIME, 7), 'OPEN', LOCALTIME, '[FE] Week 1');


/*
---- 기본 이슈 생성----
*/
INSERT INTO issue.issue
    (author_id, created_at, content, `status`, title, milestone_id)
VALUES ('78953393', LOCALTIME, '검봉 이슈 내용1', 'OPEN', '검봉 이슈1', 1),
       ('78953393', LOCALTIME, '검봉 이슈 내용2', 'OPEN', '검봉 이슈2', 1),
       ('78953393', LOCALTIME, '검봉 이슈 내용3', 'CLOSED', '검봉 이슈3', null),
       ('85419343', LOCALTIME, '밀리 이슈 내용1', 'OPEN', '밀리 이슈 1', 2),
       ('85419343', LOCALTIME, '밀리 이슈 내용2', 'OPEN', '밀리 이슈 2', 2),
       ('85419343', LOCALTIME, '밀리 이슈 내용3', 'CLOSED', '밀리 이슈 3', null);

/*
---- 기본 코멘트 생성----
*/
INSERT INTO issue.comment
    (author_id, created_at, author, content, image, issue_id)
VALUES ('78953393', LOCALTIME, 'geombong', '안녕하세요 검봉 입니다. 테스트용 댓글 입니다1.',
        'https://avatars.githubusercontent.com/u/78953393?v=4', 1),
       ('78953393', LOCALTIME, 'geombong', '안녕하세요 검봉 입니다. 테스트용 댓글 입니다2.',
        'https://avatars.githubusercontent.com/u/78953393?v=4', 2),
       ('85419343', LOCALTIME, 'jaypedia', '안녕하세요 밀리 입니다. 테스트용 댓글 입니다1.',
        'https://avatars.githubusercontent.com/u/85419343?v=4', 4),
       ('85419343', LOCALTIME, 'jaypedia', '안녕하세요 밀리 입니다. 테스트용 댓글 입니다2.',
        'https://avatars.githubusercontent.com/u/85419343?v=4', 5);

/*
---- 기본 담당자 생성----
*/
INSERT INTO issue.assignee
    (author_id, image, title)
VALUES ('78953393', 'https://avatars.githubusercontent.com/u/78953393?v=4', 'geombong');

/*
---- 기본 이슈 담당자 연결----
*/
INSERT INTO issue.issue_assignee
    (assignee_id, issue_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4);

/*
---- 기본 이슈 레이블 연결----
*/
INSERT INTO issue.issue_label
    (issue_id, label_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (4, 5);
