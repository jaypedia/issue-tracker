import { Link } from 'react-router-dom';
import styled from 'styled-components';

import { COLOR } from '@/styles/color';
import { mixins, FONT_MIXIN } from '@/styles/mixins';

const LoginWrapper = styled.div`
  ${mixins.flexBox({ direction: 'column' })}
  width: 340px;
  margin: auto;
  height: 100vh;
`;

const LoginBox = styled.div`
  width: 340px;
  margin-top: 60px;
`;

const OrDivider = styled.div`
  color: ${COLOR.grey[300]};
  margin: 24px 0;
  position: relative;
  text-align: center;

  ::before,
  ::after {
    content: '';
    display: block;
    width: 145px;
    height: 1px;
    background-color: ${COLOR.grey[300]};
    position: absolute;
    top: 50%;
  }

  ::before {
    left: 0;
  }

  ::after {
    right: 0;
  }
`;

const SignUpLink = styled(Link)`
  ${FONT_MIXIN.small(700)};
  color: ${({ theme: { color } }) => color.text};
  text-align: center;
  display: block;
  font-style: normal;
`;

const LoginForm = styled.form`
  input {
    margin-bottom: 16px;
  }

  button {
    margin: 24px 0;
  }
`;

export { LoginWrapper, LoginBox, OrDivider, SignUpLink, LoginForm };
