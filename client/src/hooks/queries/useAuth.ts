import { useQuery } from '@tanstack/react-query';
import { fetchCheckAuth } from '../../services/auth/checkAuthFetch';

export const useAuth = (enabled = true) => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ['userAuth'],
    queryFn: () => fetchCheckAuth(),
    staleTime: 1000 * 30,
    retry: false,
    enabled,
  });

  return { userAuth: data?.user, isPending, isError, error };
};
