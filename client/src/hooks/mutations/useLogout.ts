import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router';
import { queryClient } from '../../libs/tanstackQuery';
import { postLogout } from '../../services/auth/logout.ts';
import { toast } from 'sonner';

export const useLogout = () => {
  const navigate = useNavigate();

  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: postLogout,
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ['userAuth'],
        refetchType: 'none',
      });

      navigate('/login');
    },
    onError: () => {
      toast.error('Ocorreu um erro ao sair');
    },
  });

  return { mutate, isPending, isError, error };
};
