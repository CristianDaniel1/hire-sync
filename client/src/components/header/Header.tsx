import { Link } from 'react-router';
import { Navigation } from './Navigation';
import { useAuth } from '../../hooks/queries/useAuth';
import { useLogout } from '../../hooks/mutations/useLogout';
import { SignOutIcon } from '../icons/SignOutIcon';
import { UserIcon } from '../icons/UserIcon';
import { BuildingIcon } from '../icons/BuildingIcon';

export const Header = () => {
  const { userAuth, isPending, isError } = useAuth();
  const { mutate } = useLogout();

  function handleLogout() {
    mutate();
  }

  return (
    <header className="overflow-x-clip w-full fixed z-50 h-[72px]">
      <div className="max-header px-12 h-full flex justify-between custom-shadow bg-white rounded-full mt-4 py-4">
        <Link
          to="/"
          className="h-full flex items-center text-3xl text-primary-dark"
        >
          Hire<span className="text-secundary">Sync</span>
        </Link>
        {isError || isPending ? (
          <>
            <Navigation />

            <div className="flex items-center">
              <Link
                to="/login"
                className="text-center border-transparent text-white bg-secundary hover:bg-secundary/80 border-2 px-12 py-2 rounded-lg duration-200"
              >
                Entrar
              </Link>
            </div>
          </>
        ) : (
          <div className="flex items-center gap-8 font-medium tracking-wide text-primary-dark/90">
            {userAuth?.roles.every(role => role === 'CANDIDATE') ? (
              <>
                <Link to="vagas" className="hover:text-primary duration-200">
                  Portal de vagas
                </Link>
                <Link
                  to="/candidatos/perfil"
                  className="hover:text-primary duration-200 flex gap-1 items-center"
                >
                  <UserIcon />
                  Meu perfil
                </Link>
              </>
            ) : (
              <Link
                to="/minha-empresa/perfil"
                className="hover:text-primary duration-200 flex gap-1 items-center"
              >
                <BuildingIcon />
                Minha Empresa
              </Link>
            )}
            <button
              onClick={handleLogout}
              className="flex items-center gap-1 hover:text-primary duration-200 "
            >
              <SignOutIcon /> Sair
            </button>
          </div>
        )}
      </div>
    </header>
  );
};
