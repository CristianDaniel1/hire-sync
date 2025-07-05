import { Link } from 'react-router';
import { useJobsCompany } from '../../hooks/queries/useJobsCompany';
import {
  partialDateFormatter,
  translateContractType,
  translateWorkMode,
} from '../../utils/formatting';
import { BagIcon } from '../icons/BagIcon';
import { BuildingIcon } from '../icons/BuildingIcon';
import { MonitorIcon } from '../icons/Monitor';

export const MyJobs = ({ companyId }: { companyId: string }) => {
  const { data: jobs } = useJobsCompany({ id: companyId });

  return (
    <section className="bg-white rounded-xl">
      <h2 className="text-2xl font-semibold mb-4">Minhas Vagas</h2>
      {jobs?.length === 0 ? (
        <p>Você ainda não possui vagas cadastradas.</p>
      ) : (
        <ul className="grid grid-cols-4 gap-3 divide-gray-200">
          {jobs?.map(job => (
            <li key={job.id} className="border rounded-lg bg-white">
              <Link to={`/vagas/${job.id}`} className="h-full">
                <div className="pb-3 p-4">
                  <div className="flex gap-2 items-center">
                    <h3 className="font-medium text-primary-dark/60 truncate">
                      {job.companyName}
                    </h3>
                  </div>
                  <h4 className="text-base font-medium truncate">
                    {job.title}
                  </h4>
                </div>
                <div className="px-4 flex items-center text-sm gap-1 font-medium text-primary-dark/80 truncate">
                  <BagIcon className="size-4" />
                  Contrato - {translateContractType(job.contractType)}
                </div>
                <div className="items-center gap-4 flex pb-4 p-4 border-b text-sm border-b-primary-dark/10">
                  <div className="max-w-[50%] truncate">{job.location}</div>
                  <div className="flex items-center gap-1">
                    {translateWorkMode(job.workMode) === 'Presencial' ? (
                      <BuildingIcon className="size-5" />
                    ) : (
                      <MonitorIcon className="size-5" />
                    )}{' '}
                    {translateWorkMode(job.workMode)}
                  </div>
                </div>
                <div className="p-4 text-sm text-primary-dark/80">
                  Atualizado em {partialDateFormatter(job.modifiedAt)}
                </div>
              </Link>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
};
