import type { AxiosResponse } from 'axios';
import type { JobApplicationResponse } from '../../types';
import { axiosInstance } from '../../libs/axios';
import axios from 'axios';
import { CustomError } from '../../utils/CustomError';

export const createApplication = async ({ jobId }: { jobId: string }) => {
  try {
    const response: AxiosResponse<JobApplicationResponse> =
      await axiosInstance.post('applications', {
        jobId,
        status: 'PENDING',
      });

    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw new CustomError(
        error.response?.data.message,
        error.response?.status
      );
    }
  }
};
