import type { AxiosResponse } from 'axios';
import type { JobReport } from '../../types';
import { axiosInstance } from '../../libs/axios';
import axios from 'axios';
import { CustomError } from '../../utils/CustomError';

interface CompanyIdFetchParams {
  id: string;
  signal: AbortSignal;
}

export const fetchJobReportByCompany = async ({
  id,
  signal,
}: CompanyIdFetchParams) => {
  try {
    const response: AxiosResponse<JobReport[]> = await axiosInstance.get(
      `jobs/report/company/${id}`,
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
