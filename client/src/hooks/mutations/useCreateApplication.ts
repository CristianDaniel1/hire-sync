import { useMutation } from '@tanstack/react-query';
import { queryClient } from '../../libs/tanstackQuery';
import { toast } from 'sonner';
import type { CustomError } from '../../utils/CustomError';
import { createApplication } from '../../services/jobApplications/createApplication';

export const useCreateApplication = () => {
  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: createApplication,
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ['applications'],
      });

      toast.success('Candidatura feita com sucesso! A empresa foi notificada');
    },
    onError: (error: CustomError) => {
      if (error.status === 401) toast.error('É necessário fazer o login');
      else toast.error(error.message);
    },
  });

  return { mutate, isPending, isError, error };
};
