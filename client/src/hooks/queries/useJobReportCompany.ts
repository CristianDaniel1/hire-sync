import { useQuery } from '@tanstack/react-query';
import { fetchJobReportByCompany } from '../../services/jobs/jobReportCompany';

export const useJobReportCompany = ({ id }: { id: string }) => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ['reports', { companyId: id }],
    retry: 2,
    queryFn: ({ signal }) => fetchJobReportByCompany({ id, signal }),
    staleTime: 1000 * 60 * 10,
  });

  return { data, isPending, isError, error };
};
