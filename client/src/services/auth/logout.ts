import axios from 'axios';
import { axiosInstance } from '../../libs/axios';
import { CustomError } from '../../utils/CustomError';

export const postLogout = async () => {
  try {
    const response = await axiosInstance.post('auth/logout');

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
