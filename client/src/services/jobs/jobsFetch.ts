import type { AxiosResponse } from 'axios';
import type { PaginatedJobsResponse, WorkMode } from '../../types';
import { axiosInstance } from '../../libs/axios';
import axios from 'axios';
import { CustomError } from '../../utils/CustomError';

export interface FetchJobsParams {
  page?: number;
  limit?: number;
  title?: string;
  location?: string;
  workMode?: WorkMode | null;
}

interface FetchJobs extends FetchJobsParams {
  signal: AbortSignal;
}

export const fetchJobs = async ({
  page = 0,
  limit = 12,
  title,
  location,
  workMode,
  signal,
}: FetchJobs) => {
  let url = `jobs?page=${page}&limit=${limit}`;

  if (title) url += `&title=${title}`;
  if (location) url += `&location=${location}`;
  if (workMode) url += `&workMode=${workMode}`;

  try {
    const response: AxiosResponse<PaginatedJobsResponse> =
      await axiosInstance.get(url, { signal });

    const { page: currentPage, isLast: lastPage } = response.data;

    const nextCursor = lastPage ? undefined : currentPage + 1;

    return { data: response.data, nextCursor };
  } catch (error: unknown) {
    if (axios.isAxiosError(error)) {
      throw new CustomError('Erro ao buscar vagas', error.response?.status);
    }
  }
};
