import axios from 'axios';
import moment from 'moment';
import { useQuery } from 'react-query';

import * as S from './style';

import Button from '@/components/common/Button';
import ProgressBar from '@/components/common/ProgressBar';
import Navbar from '@/components/Navbar';
import * as I from '@/icons/Milestone';
import { MainWrapper, InnerContainer } from '@/styles/common';
import { ListHeader, ListContainer, Item } from '@/styles/list';
import { getRelativeTime } from '@/utils/issue';

// TODO: 반복되는 fetch 로직 Refactoring
const fetchAPI = () => {
  return axios.get(`/api/milestones`);
};

// TODO: type check
const MilestoneDate = ({ dueDate }: string): JSX.Element => {
  const today = new Date();
  const isPast = new Date(dueDate) < today;

  if (isPast) {
    return (
      <S.MilestoneMetaItem>
        <I.Warning />
        <S.MetaSentence type="warning">
          Past due by {moment(today).diff(dueDate, 'days')} days
        </S.MetaSentence>
      </S.MilestoneMetaItem>
    );
  }

  return (
    <S.MilestoneMetaItem>
      <I.Calendar />
      <S.MetaSentence>Due by {moment(dueDate).format('MMMM D, YYYY')}</S.MetaSentence>
    </S.MilestoneMetaItem>
  );
};

// TODO: List Header 수정 - Open, Closed로 나누기
const Milestones = () => {
  const { data, isLoading } = useQuery('milestones', fetchAPI);
  console.log(data);

  return (
    <MainWrapper>
      <InnerContainer>
        <Navbar btnText="New Milestone" />
        <ListContainer>
          <ListHeader type="large">{data?.data.allMileStonesCount} Milestones</ListHeader>
          {data?.data.milestones.map(
            ({ id, title, dueDate, updatedAt, description, openIssueCount, closedIssueCount }) => (
              <Item key={id} type="milestone">
                <S.MilestoneInfoBox>
                  <S.MilestoneTitle>{title}</S.MilestoneTitle>
                  <S.MilestoneMetaBox>
                    <MilestoneDate dueDate={dueDate} />
                    <S.MilestoneMetaItem>
                      <I.Clock />
                      <S.MetaSentence>Last updated {getRelativeTime(updatedAt)}</S.MetaSentence>
                    </S.MilestoneMetaItem>
                  </S.MilestoneMetaBox>
                  <p>{description}</p>
                </S.MilestoneInfoBox>

                <S.ProgressBarBox>
                  <ProgressBar
                    size="large"
                    openIssueCount={openIssueCount}
                    closedIssueCount={closedIssueCount}
                  />
                  <S.ButtonBox>
                    <Button btnStyle="text" textColor="primary" text="Edit" />
                    <Button btnStyle="text" textColor="primary" text="Close" />
                    <Button btnStyle="text" textColor="warning" text="Delete" />
                  </S.ButtonBox>
                </S.ProgressBarBox>
              </Item>
            ),
          )}
        </ListContainer>
      </InnerContainer>
    </MainWrapper>
  );
};

export default Milestones;
