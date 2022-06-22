import styled from 'styled-components';

import { COLOR } from '@/styles/color';
import { mixins } from '@/styles/mixins';

const FilterBar = styled.div`
  ${mixins.flexBox({ justifyContent: 'flex-start', alignItems: 'strech' })}
  width: 600px;
  border: 1px solid ${({ theme: { color } }) => color.line};
  border-radius: 11px;
  background: ${({ theme: { color } }) => color.inputBg};
`;

const SearchInputContainer = styled.div`
  position: relative;
  ${mixins.flexBox({})}
  width: 100%;
  border-left: 1px solid ${({ theme: { color } }) => color.line};
  gap: 8px;
  color: ${({ theme: { color } }) => color.text};

  svg {
    position: absolute;
    left: 24px;
    top: 0;
    height: 100%;
    flex-shrink: 0;
    color: ${COLOR.grey[300]};
  }

  input {
    width: 100%;
    height: 100%;
    padding: 0 24px 0 48px;
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

export { FilterBar, SearchInputContainer };
