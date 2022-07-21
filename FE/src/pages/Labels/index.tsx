import axios from 'axios';
import { useQuery } from 'react-query';

import * as S from './style';

import Button from '@/components/common/Button';
import Label from '@/components/common/Label';
import LabelForm from '@/components/LabelForm';
import Navbar from '@/components/Navbar';
import useBoolean from '@/hooks/useBoolean';
import { MainWrapper, InnerContainer } from '@/styles/common';
import { ListHeader, ListContainer, Item } from '@/styles/list';

// TODO: 반복되는 fetch 로직 Refactoring
const fetchAPI = () => {
  return axios.get(`/api/labels`);
};

const LabelItem = ({ id, title, backgroundColor, textColor, description }) => {
  const { booleanState: isFormOpen, setTrue, setFalse } = useBoolean(false);

  const handleEditClick = () => {
    setTrue();
  };

  return (
    <Item key={id} type="large">
      {isFormOpen ? (
        <LabelForm
          type="edit"
          onCancel={setFalse}
          labelName={title}
          description={description}
          backgroundColor={backgroundColor}
          color={textColor}
        />
      ) : (
        <>
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
            <Button isText text="Edit" onClick={handleEditClick} />
            <Button isText text="Delete" />
          </S.ButtonsWrapper>
        </>
      )}
    </Item>
  );
};

const Labels = () => {
  const { data, isLoading } = useQuery('labels', fetchAPI);
  const { booleanState: isFormOpen, setFalse, setToggle } = useBoolean(false);

  const handleNewLabelClick = () => {
    setToggle();
  };

  return (
    <MainWrapper>
      <InnerContainer>
        <Navbar btnText="New Label" onClick={handleNewLabelClick} />
        {isFormOpen && <LabelForm type="create" onCancel={setFalse} />}
        <ListContainer>
          <ListHeader type="large">{data?.data.labelCount} Labels</ListHeader>
          {data?.data.labels.map(({ id, title, backgroundColor, textColor, description }) => (
            <LabelItem
              id={id}
              title={title}
              backgroundColor={backgroundColor}
              textColor={textColor}
              description={description}
            />
          ))}
        </ListContainer>
      </InnerContainer>
    </MainWrapper>
  );
};

export default Labels;
