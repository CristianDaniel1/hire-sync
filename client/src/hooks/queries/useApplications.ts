import { useQuery } from '@tanstack/react-query';
import { applicationFetch } from '../../services/jobApplications/applicationFetch';

export const useApplications = ({ id }: { id: string }) => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ['applications', { id }],
    retry: 1,
    queryFn: ({ signal }) => applicationFetch({ id, signal }),
    staleTime: 1000 * 60 * 10,
  });

  return {
    applications: data,
    isPending,
    isError,
    error,
  };
};
