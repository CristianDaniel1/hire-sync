import { MyJobs } from '../components/companies/MyJobs';
import { ReportCompany } from '../components/companies/ReportCompany';
import { AtIcon } from '../components/icons/AtIcon';
import { GlobeIcon } from '../components/icons/GlobeIcon';
import { useAuth } from '../hooks/queries/useAuth';

import img from '../assets/company.jpg';

const MyCompany = () => {
  const { userAuth: company } = useAuth();

  if (!company || company?.type !== 'COMPANY') {
    return (
      <div className="text-xl text-center py-24 text-red-700 font-semibold">
        Somente para Empresas!
      </div>
    );
  }
  return (
    <div className="min-h-screen pb-20">
      <div className="relative">
        <img
          src={img}
          alt=""
          className="absolute w-full h-full object-cover brightness-[.3] -z-10"
        />
        <div className="pt-28 pb-6">
          <div
            className={`flex text-primary-dark/80 items-center gap-3 custom-shadow rounded-lg border max-container-2  bg-white h-40 ${
              company.logoImage ? 'px-2' : 'px-4'
            }`}
          >
            {company.logoImage && (
              <div className="size-40">
                <img
                  src={company.logoImage}
                  alt={company.logoImage}
                  className="w-full h-full object-contain"
                />
              </div>
            )}
            <div>
              <h1 className="text-4xl font-semibold pb-2">{company.name}</h1>
              <div className="text-sm text-primary-dark-2 font-normal flex items-center gap-1 pb-1">
                <AtIcon className="size-5" /> <span>{company.email}</span>
              </div>
              {company.website && (
                <div className="text-sm">
                  <a
                    className="underline text-sm flex items-center gap-1 text-primary-dark-2"
                    href={company.website}
                  >
                    <GlobeIcon className="size-5" /> {company.website}
                  </a>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>

      <div className="max-container padding-x grid gap-10">
        <section className="bg-white rounded-xl shadow p-6">
          <div className="text-primary-dark grid grid-cols-2 gap-3">
            <div>
              <strong>CNPJ:</strong> {company.cnpj}
            </div>
            <div>
              <strong>Telefone:</strong> {company.phone}
            </div>
            <div>
              <strong>Endereço:</strong> {company.address}
            </div>
            <div>
              <strong>Tipo:</strong>{' '}
              {company.isHeadOffice ? 'Matriz' : 'Filial'}
            </div>
            <div>
              <strong>Grupo:</strong> {company.groupName}
            </div>
          </div>
        </section>
        <div>
          <ReportCompany companyId={company.id} />
        </div>
        <div>
          <MyJobs companyId={company.id} />
        </div>
      </div>
    </div>
  );
};

export default MyCompany;
