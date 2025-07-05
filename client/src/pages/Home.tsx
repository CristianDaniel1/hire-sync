import { Link } from 'react-router';
import { Search } from '../components/jobs/Search';
import { useJobs } from '../hooks/queries/useJobs';
import {
  currencyFormatter,
  translateContractType,
  translateWorkMode,
} from '../utils/formatting';

const Home = () => {
  const { jobs } = useJobs({ page: 1, limit: 3 });

  return (
    <>
      <section className="bg-white">
        <div className="max-container padding-x pt-24 pb-16 min-h-[50vh]">
          <div className="py-10 border-b">
            <Search />
          </div>
          <div className="flex justify-between items-center font-medium pt-4 pb-4">
            <h2 className="text-primary-dark">Sugestões de vagas</h2>
            <Link to="/vagas" className="px-5 py-2 rounded-lg">
              Ver todas as vagas
            </Link>
          </div>
          <ul className="grid grid-cols-3 gap-6 text-white">
            {jobs &&
              jobs.length > 0 &&
              jobs.map(job => (
                <li
                  className="shadow-md rounded-lg px-6 pt-3 pb-6 bg-white flex flex-col justify-between"
                  key={job?.id}
                >
                  <Link to={`/vagas/${job!.id}`}>
                    <div className="flex items-center gap-2">
                      {job?.companyLogo && (
                        <div className="size-12">
                          <img
                            src={job?.companyLogo}
                            alt="vaga"
                            className="object-contain h-full w-full"
                          />
                        </div>
                      )}
                      <h3 className="font-medium text-primary-dark/60 truncate">
                        {job!.companyName}
                      </h3>
                    </div>
                    <h4 className="text-lg font-semibold text-primary-dark pb-3 truncate">
                      {job?.title}
                    </h4>
                    <p className="text-sm text-gray-600">
                      {translateWorkMode(job!.workMode)} |{' '}
                      {translateContractType(job!.contractType)} |{' '}
                      {currencyFormatter(job!.salary)}
                    </p>
                  </Link>
                </li>
              ))}
          </ul>
        </div>
      </section>
      <section className="bg-primary-dark">
        <div className="max-container padding-x py-16">
          <h2 className="text-white text-2xl font-medium pb-12">
            Encontre o seu emprego em áreas da tecnologia
          </h2>
          <div className="grid gap-8 text-white">
            <div className="rounded-lg py-20 px-10 bg-white flex flex-col justify-between relative custom-shadow">
              <h4 className="text-4xl font-semibold z-10 relative pb-4">
                Dados & BI
              </h4>
              <p className="text-sm z-10">
                Analistas, engenheiros e cientistas de dados.
              </p>
              <img
                src="https://media.istockphoto.com/id/1425235289/photo/data-analyst-working-on-business-analytics-dashboard-with-charts-metrics-and-kpi-to-analyze.jpg?s=612x612&w=0&k=20&c=fKMxXdm1SrZE9unWyLWUPPzK6RTy8OsBBaKWcpH0O4s="
                alt=""
                className="absolute inset-0 w-full h-full object-cover brightness-75 z-0"
              />
            </div>
            <div className="rounded-lg py-20 px-10 relative overflow-hidden custom-shadow">
              <h4 className="text-4xl font-semibold z-10 relative pb-4 text-end">
                Desenvolvimento de Software
              </h4>
              <p className="z-10 relative text-end">
                Explore vagas para frontend, backend, full stack e mais.
              </p>
              <img
                src="https://www.nethues.com/blog/app/uploads/2023/11/Nethues-Blog-Image-Top-10-Software-Development-Frameworks-That-Will-Shape-the-Tech-Industry-in-2024-1-1-scaled.jpg"
                alt=""
                className="absolute inset-0 w-full h-full object-cover z-0"
              />
            </div>
            <div className="rounded-lg py-20 px-10 bg-white flex flex-col justify-between relative custom-shadow">
              <h4 className="text-4xl font-semibold z-10 relative pb-4">
                Design UX/UI
              </h4>
              <p className="text-sm z-10">
                Oportunidades para designers focados em produto digital.
              </p>
              <img
                src="https://media.istockphoto.com/id/1305999733/photo/web-design-desktop.jpg?s=612x612&w=0&k=20&c=mdGnzSrhUGijPuXHR3vA_hrQyshToudcJc8A50L0a9I="
                alt=""
                className="absolute inset-0 w-full h-full object-cover brightness-50 z-0"
              />
            </div>
          </div>
        </div>
      </section>
      <section className="bg-white">
        <div className="max-container padding-x padding-y n"></div>
      </section>
    </>
  );
};

export default Home;
