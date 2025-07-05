import type { AxiosResponse } from 'axios';
import { axiosInstance } from '../../libs/axios';
import type { Job } from '../../types';
import { CustomError } from '../../utils/CustomError';
import axios from 'axios';

interface JobIdFetchParams {
  id: string;
  signal: AbortSignal;
}

export const fetchJobId = async ({ id, signal }: JobIdFetchParams) => {
  try {
    const response: AxiosResponse<Job> = await axiosInstance.get(`jobs/${id}`, {
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
