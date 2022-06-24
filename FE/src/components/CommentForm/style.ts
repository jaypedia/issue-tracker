import styled from 'styled-components';

import { mixins } from '../../styles/mixins';

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

const CommentWrapper = styled.div`
  ${mixins.flexBox({ direction: 'column', alignItems: 'flex-end' })}
  width: 100%;
  gap: 15px;
`;

export { FlexWrapper, NewIssueForm, CommentWrapper };
