import styled from 'styled-components';

import { COLOR } from '@/styles/color';
import { mixins } from '@/styles/mixins';

const SearchForm = styled.form`
  position: relative;
  ${mixins.flexBox({})}
  width: 100%;
  border-left: 1px solid ${({ theme: { color } }) => color.line};
  gap: 8px;
  color: ${({ theme: { color } }) => color.text};

  & > :first-child {
    position: absolute;
    left: 24px;
    top: 0;
    height: 100%;
    flex-shrink: 0;
    color: ${COLOR.grey[300]};
  }

  & > :last-child {
    position: absolute;
    right: 24px;
    top: 0;
    color: ${COLOR.grey[300]};
    height: 100%;
  }

  input {
    width: 100%;
    height: 100%;
    padding: 0 48px;
    border-radius: 0 11px 11px 0;
    color: ${({ theme: { color } }) => color.text};

    ::placeholder {
      color: ${COLOR.grey[300]};
    }

    :focus {
      background: ${({ theme: { color } }) => color.bg};

      + svg {
        color: ${({ theme: { color } }) => color.text};
      }
    }
  }
`;

export { SearchForm };
