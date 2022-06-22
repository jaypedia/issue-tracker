import styled from 'styled-components';

import { mixins } from '../../styles/mixins';

const ColumnWrapper = styled.div`
  ${mixins.flexBox({ direction: 'column' })}
  width: 100%;
`;

const FlexStartStyle = `
${mixins.flexBox({ alignItems: 'flex-start' })}
width: 100%;
`;

const FlexWrapper = styled.div`
  ${FlexStartStyle}
  gap: 20px;
`;

const NewIssueForm = styled.form`
  ${FlexStartStyle}
  gap: 40px;
`;

const NewIssueContentsWrapper = styled.div`
  ${mixins.flexBox({ direction: 'column', alignItems: 'flex-end' })}
  width: 100%;
  gap: 15px;
`;

export { ColumnWrapper, FlexWrapper, NewIssueForm, NewIssueContentsWrapper };
