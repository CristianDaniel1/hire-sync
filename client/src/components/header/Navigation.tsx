import { Link } from 'react-router';

export const Navigation = () => {
  return (
    <nav className="text-primary-dark text-lg flex items-center w-full">
      <ul className="flex gap-4 lg:gap-10 items-center justify-center w-full">
        <li>
          <Link
            to="/cadastro-candidato"
            className="hover:text-primary duration-200"
          >
            Candidatos
          </Link>
        </li>
        <li>
          <Link
            to="/cadastro-empresa"
            className="hover:text-primary duration-200"
          >
            Empresas
          </Link>
        </li>
        <li>
          <Link to="vagas" className="hover:text-primary duration-200">
            Ver vagas
          </Link>
        </li>
      </ul>
    </nav>
  );
};
