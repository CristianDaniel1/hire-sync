import axios, { type AxiosResponse } from 'axios';
import { CustomError } from '../../utils/CustomError';
import { axiosInstance } from '../../libs/axios';
import type { Job } from '../../types';

interface CompanyIdFetchParams {
  id: string;
  signal: AbortSignal;
}

export const fetchJobsByCompany = async ({
  id,
  signal,
}: CompanyIdFetchParams) => {
  try {
    const response: AxiosResponse<Job[]> = await axiosInstance.get(
      `jobs/company/${id}`,
      {
        signal,
      }
    );
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
