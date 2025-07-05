import type { AxiosResponse } from 'axios';
import type { JobApplicationResponse } from '../../types';
import { axiosInstance } from '../../libs/axios';
import axios from 'axios';
import { CustomError } from '../../utils/CustomError';

interface CandidateIdFetchParams {
  id: string;
  signal: AbortSignal;
}

export const applicationFetch = async ({
  id,
  signal,
}: CandidateIdFetchParams) => {
  try {
    const response: AxiosResponse<JobApplicationResponse[]> =
      await axiosInstance.get(`applications/candidate/${id}`, {
        signal,
      });
    return response.data;
  } catch (error: unknown) {
    if (axios.isAxiosError(error)) {
      throw new CustomError(
        error.response?.data.message,
        error.response?.status
      );
    }
  }
};
