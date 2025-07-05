import type { AxiosResponse } from 'axios';
import { axiosInstance } from '../../libs/axios';
import { CustomError } from '../../utils/CustomError';
import axios from 'axios';
import type { ResumeResponse } from '../../types';

interface CandidateIdFetchParams {
  id: string;
  signal: AbortSignal;
}

export const fetchResume = async ({ id, signal }: CandidateIdFetchParams) => {
  try {
    const response: AxiosResponse<ResumeResponse> = await axiosInstance.get(
      `resumes/candidate/${id}`,
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
