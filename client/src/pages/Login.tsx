import { useForm, type SubmitHandler } from 'react-hook-form';
import { Button } from '../components/ui/Button';
import { Input } from '../components/ui/Input';
import { InvalidInput } from '../components/ui/InvalidInput';
import { LoginSchema, type LoginType } from '../schemas/login';
import { zodResolver } from '@hookform/resolvers/zod';
import { PasswordInput } from '../components/ui/PasswordInput';
import { Link } from 'react-router';
import { useLogin } from '../hooks/mutations/useLogin';

const Login = () => {
  const { mutate, isError } = useLogin();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginType>({
    resolver: zodResolver(LoginSchema),
    defaultValues: {
      email: '',
      password: '',
    },
  });

  const onSubmit: SubmitHandler<LoginType> = data => {
    mutate(data);
  };

  return (
    <section className="padding-y max-container">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="mt-14 px-8 md:px-14 py-8 lg:py-16 xl:w-[35rem] mx-auto rounded-md h-full custom-shadow"
      >
        <h2 className="text-3xl pb-12 text-center font-medium">
          Entrar com sua conta
        </h2>
        <div className="pb-4">
          <Input
            id="email"
            label="Seu e-mail"
            type="email"
            {...register('email')}
            autoComplete="email"
            placeholder="Digite o seu e-mail"
          />
          {errors.email && <InvalidInput text={errors.email.message!} />}
        </div>
        <div className="relative pb-4">
          <PasswordInput
            id="password"
            label="Sua senha"
            placeholder="Digite a sua senha"
            autoComplete="current-password"
            {...register('password')}
          />
          {errors.password && <InvalidInput text={errors.password.message!} />}
        </div>
        <Button
          className="text-lg my-6 w-full flex justify-center items-center gap-3 py-3 rounded-lg"
          bgColor
        >
          Entrar
        </Button>
        {isError && (
          <div className="text-center">
            <InvalidInput text={'Dados incorretos'} />
          </div>
        )}
        <div className="text-center mt-6">
          <div className="text-primary-dark font-medium pb-4">
            Novo(a) em HireSync?
          </div>
          <div>
            Cadastre-se como{' '}
            <Link
              to="/cadastro"
              className="text-primary font-semibold sm:hover:text-secundary duration-200 active:text-secundary"
            >
              Candidato
            </Link>{' '}
            ou{' '}
            <Link
              to="/cadastro"
              className="text-primary font-semibold sm:hover:text-secundary duration-200 active:text-secundary"
            >
              Empresa
            </Link>
          </div>
        </div>
        <p className="text-center mt-6"></p>
      </form>
    </section>
  );
};

export default Login;
