import Loading from '@/components/common/Loading';
import LabelItem from '@/components/LabelItem';
import LabelForm from '@/components/LabelItem/LabelForm';
import Navbar from '@/components/Navbar';
import useBoolean from '@/hooks/useBoolean';
import { useGetLabel } from '@/hooks/useLabel';
import { MainWrapper, InnerContainer } from '@/styles/common';
import { ListHeader, ListContainer } from '@/styles/list';
import { ILabel } from '@/types/labelTypes';

const Labels = () => {
  const { data, isLoading } = useGetLabel();
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
          {isLoading || !data ? (
            <Loading />
          ) : (
            <>
              <ListHeader type="large">{data.labelCount} Labels</ListHeader>
              {data.labels.map(({ id, title, backgroundColor, textColor, description }: ILabel) => (
                <LabelItem
                  id={id}
                  key={id}
                  title={title}
                  backgroundColor={backgroundColor}
                  textColor={textColor}
                  description={description}
                />
              ))}
            </>
          )}
        </ListContainer>
      </InnerContainer>
    </MainWrapper>
  );
};

export default Labels;
