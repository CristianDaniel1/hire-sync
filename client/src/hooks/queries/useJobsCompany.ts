import { useQuery } from '@tanstack/react-query';
import { fetchJobsByCompany } from '../../services/jobs/jobCompanyFetch';

export const useJobsCompany = ({ id }: { id: string }) => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ['jobs', { companyId: id }],
    retry: 2,
    queryFn: ({ signal }) => fetchJobsByCompany({ id, signal }),
    staleTime: 1000 * 60 * 10,
  });

  return { data, isPending, isError, error };
};
