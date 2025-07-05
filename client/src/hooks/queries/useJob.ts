import { useQuery } from '@tanstack/react-query';
import { fetchJobId } from '../../services/jobs/JobFetch';

export const useJob = ({ id }: { id: string }) => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ['jobs', { id }],
    retry: 2,
    queryFn: ({ signal }) => fetchJobId({ id, signal }),
    staleTime: 1000 * 60 * 10,
  });

  return { data, isPending, isError, error };
};
