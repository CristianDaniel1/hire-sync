import { redirect } from 'react-router';
import { queryClient } from '../../libs/tanstackQuery';
import { fetchCheckAuth } from '../../services/auth/checkAuthFetch';
import { CustomError } from '../CustomError';

export const createAuthLoader = (allowedRoles?: string[]) => async () => {
  try {
    const data = await queryClient.fetchQuery({
      queryKey: ['userAuth'],
      queryFn: () => fetchCheckAuth(allowedRoles),
    });

    if (!data?.isAllowed) return redirect('/');

    return data;
  } catch (error: unknown) {
    if (error instanceof Error) console.log(error.message);
    return redirect('/login');
  }
};

export const loggedLoader = async () => {
  try {
    const data = await queryClient.fetchQuery({
      queryKey: ['userAuth'],
      queryFn: () => fetchCheckAuth(),
    });

    if (data?.user) return redirect('/perfil');

    return;
  } catch (error: unknown) {
    if (error instanceof CustomError) {
      if (error.status === 401 || error.status === 403) return;
      throw error;
    }
  }
};
