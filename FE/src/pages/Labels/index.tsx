import axios from 'axios';
import { useQuery } from 'react-query';

import * as S from './style';

import Label from '@/components/common/Label';
import Navbar from '@/components/Navbar';
import { MainWrapper, InnerContainer } from '@/styles/common';
import { ListHeader, ListContainer, Item } from '@/styles/list';

// TODO: 반복되는 fetch 로직 Refactoring
const fetchAPI = () => {
  return axios.get(`/api/labels`);
};

const Labels = () => {
  const { data, isLoading } = useQuery('labels', fetchAPI);

  return (
    <MainWrapper>
      <InnerContainer>
        <Navbar btnText="New Label" />
        <ListContainer>
          <ListHeader type="large">{data?.data.labelCount} Labels</ListHeader>
          {data?.data.labels.map(({ title, backgroundColor, textColor, description }, i) => (
            <Item key={i} type="large">
              <S.LabelWrapper>
                <Label
                  size="small"
                  title={title}
                  backgroundColor={backgroundColor}
                  textColor={textColor}
                />
              </S.LabelWrapper>
              <S.Description>{description}</S.Description>
              <S.ButtonsWrapper>
                <S.TextButton>Edit</S.TextButton>
                <S.TextButton>Delete</S.TextButton>
              </S.ButtonsWrapper>
            </Item>
          ))}
        </ListContainer>
      </InnerContainer>
    </MainWrapper>
  );
};

export default Labels;
