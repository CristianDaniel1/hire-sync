import axios, { type AxiosResponse } from 'axios';
import { axiosInstance } from '../../libs/axios';
import { CustomError } from '../../utils/CustomError';
import type { UserAuth } from '../../types';

export const fetchCheckAuth = async (allowedRoles?: string[]) => {
  try {
    const res: AxiosResponse<UserAuth> = await axiosInstance.get('/auth/check');
    const user = res.data;
    let isAllowed = true;

    user.roles.forEach(role => {
      if (allowedRoles && !allowedRoles.includes(role)) {
        isAllowed = false;
      }
    });

    if (user.roles.includes('CANDIDATE')) user.type = 'CANDIDATE';
    if (user.roles.includes('COMPANY')) user.type = 'COMPANY';

    return { user, isAllowed };
  } catch (error: unknown) {
    if (axios.isAxiosError(error)) {
      throw new CustomError(
        error.response?.data.message,
        error.response?.status
      );
    }
  }
};
