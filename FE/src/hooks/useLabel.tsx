import { useMutation, useQueryClient, useQuery } from 'react-query';

import { fetchAPI } from '@/apis/common';
import { deleteLabel } from '@/apis/labelApi';
import { API } from '@/constants/api';
import { LabelDataType } from '@/types/labelTypes';

const useGetLabel = () => {
  return useQuery([API.LABELS], () => fetchAPI<LabelDataType>(API.LABELS));
};

const useDeleteLabel = (id: number) => {
  const queryClient = useQueryClient();
  return useMutation(() => deleteLabel(id), {
    onSuccess: () => {
      queryClient.invalidateQueries('labels');
    },
  });
};

const useRefetchLabel = () => {
  const queryClient = useQueryClient();
  return useMutation(() => fetchAPI('labels'), {
    onSuccess: () => {
      queryClient.invalidateQueries('labels');
    },
  });
};

export { useGetLabel, useDeleteLabel, useRefetchLabel };
