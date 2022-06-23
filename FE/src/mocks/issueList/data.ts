export const mockIssueList = [
  {
    id: 1,
    issueTitle: 'Implement Home page',
    author: 'J',
    issueCreateTime: '2022-06-23 17:55:02',
    labels: [
      {
        id: 1,
        title: '✨ Feature',
        backgroundColor: '#a2eeef',
        textColor: '#0A071B',
      },
      {
        id: 2,
        title: '🎨 Design',
        backgroundColor: '#FEF2C0',
        textColor: '#0A071B',
      },
      {
        id: 3,
        title: '🧊 EPIC',
        backgroundColor: '#23026B',
        textColor: '#FEFEFE',
      },
    ],
    mileStoneTitle: 'Week 1',
    assignees: [
      {
        id: 1,
        userId: 'J',
        image: 'https://avatars.githubusercontent.com/u/68211156?s=40&v=4',
      },
      {
        id: 2,
        userId: 'Millie',
        image: 'https://avatars.githubusercontent.com/u/85419343?s=80&v=4',
      },
    ],
    authorImage: 'https://avatars.githubusercontent.com/u/68211156?s=40&v=4',
    issueStatus: 'open',
  },
  {
    id: 2,
    issueTitle: 'Implement NewIssue page',
    author: 'Millie',
    issueCreateTime: '2022-06-23 02:33:02',
    labels: [
      {
        id: 1,
        title: '✨ Feature',
        backgroundColor: '#a2eeef',
        textColor: '#0A071B',
      },
      {
        id: 2,
        title: '🎨 Design',
        backgroundColor: '#FEF2C0',
        textColor: '#0A071B',
      },
    ],
    mileStoneTitle: 'Week 1',
    assignees: [
      {
        id: 1,
        userId: 'J',
        image: 'https://avatars.githubusercontent.com/u/68211156?s=40&v=4',
      },
      {
        id: 2,
        userId: 'Millie',
        image: 'https://avatars.githubusercontent.com/u/85419343?s=80&v=4',
      },
    ],
    issueWriterImage: 'https://avatars.githubusercontent.com/u/85419343?s=80&v=4',
    issueStatus: 'close',
  },
  {
    id: 3,
    issueTitle: 'Common components',
    author: 'J',
    issueCreateTime: '2022-06-23 02:33:02',
    labels: [
      {
        id: 1,
        title: '✨ Feature',
        backgroundColor: '#a2eeef',
        textColor: '#0A071B',
      },
      {
        id: 2,
        title: '🎨 Design',
        backgroundColor: '#FEF2C0',
        textColor: '#0A071B',
      },
    ],
    mileStoneTitle: 'Week 1',
    assignees: [
      {
        id: 1,
        userId: 'J',
        image: 'https://avatars.githubusercontent.com/u/68211156?s=40&v=4',
      },
      {
        id: 2,
        userId: 'Millie',
        image: 'https://avatars.githubusercontent.com/u/85419343?s=80&v=4',
      },
    ],
    authorImage: 'https://avatars.githubusercontent.com/u/68211156?s=40&v=4',
    issueStatus: 'open',
  },
  {
    id: 4,
    issueTitle: 'Webpack comfiguration',
    author: 'Millie',
    issueCreateTime: '2022-06-23 02:33:02',
    labels: [
      {
        id: 1,
        title: '✨ Feature',
        backgroundColor: '#a2eeef',
        textColor: '#0A071B',
      },
      {
        id: 2,
        title: '🎨 Design',
        backgroundColor: '#FEF2C0',
        textColor: '#0A071B',
      },
    ],
    mileStoneTitle: 'Week 1',
    assignees: [
      {
        id: 1,
        userId: 'J',
        image: 'https://avatars.githubusercontent.com/u/68211156?s=40&v=4',
      },
      {
        id: 2,
        userId: 'Millie',
        image: 'https://avatars.githubusercontent.com/u/85419343?s=80&v=4',
      },
    ],
    issueWriterImage: 'https://avatars.githubusercontent.com/u/85419343?s=80&v=4',
    issueStatus: 'close',
  },
];

export const mockIssueDetail = {
  id: 1,
  issueTitle: '이슈 타이틀',
  issueStatus: 'open',
  issueCreateTime: 'yyyy-MM-dd HH:mm:ss',
  commentCount: 1,
  assignees: [
    {
      id: 1,
      userId: 'J',
      image: 'https://avatars.githubusercontent.com/u/68211156?s=40&v=4',
    },
    {
      id: 2,
      userId: 'Millie',
      image: 'https://avatars.githubusercontent.com/u/85419343?s=80&v=4',
    },
  ],
  labels: [
    {
      id: 1,
      name: '레이블 이름',
    },
    {
      id: 2,
      name: '레이블 이름',
    },
  ],
  mileStoneTitle: '마일스톤 이름',
  mileStoneDescription: '마일스톤 설명',
};
