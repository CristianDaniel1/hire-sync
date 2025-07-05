import { useQuery } from '@tanstack/react-query';
import { fetchResume } from '../../services/resume/resumeFetch';

export const useResume = ({ id }: { id: string }) => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ['resumes', { id }],
    retry: 1,
    queryFn: ({ signal }) => fetchResume({ id, signal }),
    staleTime: 1000 * 60 * 10,
  });

  return {
    resume: data,
    isFetchingResume: isPending,
    isErrorResume: isError,
    error,
  };
};
