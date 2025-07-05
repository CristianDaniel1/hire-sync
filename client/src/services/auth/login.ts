import axios, { type AxiosResponse } from 'axios';
import { axiosInstance } from '../../libs/axios';
import { CustomError } from '../../utils/CustomError';
import type { AuthResponse, BodyLogin } from '../../types';

export const postLogin = async ({ email, password }: BodyLogin) => {
  try {
    const response: AxiosResponse<AuthResponse> = await axiosInstance.post(
      'auth/login',
      { email, password }
    );

    return response.data.user;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw new CustomError(
        error.response?.data.message,
        error.response?.status
      );
    }
  }
};
