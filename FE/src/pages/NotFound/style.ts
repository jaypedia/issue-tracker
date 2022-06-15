import { Link } from 'react-router-dom';

import styled from 'styled-components';

import { mixins } from '@/styles/mixins';

const NotFoundWrapper = styled.div`
  ${mixins.flexBox({ direction: 'column' })}
  height: 100vh;
`;

const NotFoundText = styled.h1``;

const HomeButton = styled(Link)`
  width: 100px;
  height: 50px;
  background-color: ${({ theme: { color } }) => color.primary};
  color: ${({ theme: { color } }) => color.text};
  border-radius: 60px;
  text-align: center;
  line-height: 50px;
`;

export { NotFoundWrapper, NotFoundText, HomeButton };
