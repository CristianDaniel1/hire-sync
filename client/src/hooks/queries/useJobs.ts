import { useInfiniteQuery } from '@tanstack/react-query';
import { fetchJobs, type FetchJobsParams } from '../../services/jobs/jobsFetch';

export const useJobs = ({
  title,
  location,
  workMode,
  page,
  limit,
}: FetchJobsParams) => {
  const {
    fetchNextPage,
    hasNextPage,
    isPending,
    isFetchingNextPage,
    isError,
    error,
    data,
  } = useInfiniteQuery({
    queryKey: ['jobs', title, location, workMode, limit],
    queryFn: ({ pageParam = 0, signal }) =>
      fetchJobs({
        page: page || pageParam,
        title,
        location,
        limit,
        workMode,
        signal,
      }),
    initialPageParam: 0,
    staleTime: 1000 * 60 * 60,
    getNextPageParam: lastPage =>
      !lastPage?.data.isLast ? lastPage?.nextCursor : undefined,
  });

  return {
    fetchNextPage,
    hasNextPage,
    isPending,
    isFetchingNextPage,
    isError,
    error,
    jobs: data?.pages.flatMap(page => page?.data.content) ?? [],
  };
};
