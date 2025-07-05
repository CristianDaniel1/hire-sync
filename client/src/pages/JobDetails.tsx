import { useParams } from 'react-router';
import { Button } from '../components/ui/Button';
import { useJob } from '../hooks/queries/useJob';
import {
  currencyFormatter,
  dateStringFormatter,
  partialDateFormatter,
  translateContractType,
  translateWorkMode,
} from '../utils/formatting';
import { BuildingIcon } from '../components/icons/BuildingIcon';
import { MonitorIcon } from '../components/icons/Monitor';
import { BagIcon } from '../components/icons/BagIcon';
import { MoneyIcon } from '../components/icons/MoneyIcon';
import { GlobeIcon } from '../components/icons/GlobeIcon';
import { useAuth } from '../hooks/queries/useAuth';
import { GraduationIcon } from '../components/icons/GraduationIcon';
import { useCreateApplication } from '../hooks/mutations/useCreateApplication';

const JobDetails = () => {
  const params = useParams();
  const { userAuth, isError, isPending: isFetchUser } = useAuth();
  const { mutate, isPending: isApplicating } = useCreateApplication();

  function handleClick() {
    mutate({ jobId: job!.id });
  }

  const { data: job, isPending } = useJob({ id: params.jobId! });

  if (isPending || !job) {
    return <div className="w-full h-64" />;
  }

  const isCompany = userAuth?.type === 'COMPANY';
  const isCandidate = userAuth?.type === 'CANDIDATE';

  return (
    <section className="bg-primary-dark pt-28">
      <div className="bg-white pb-20 min-h-screen">
        <div className="max-container padding-x pt-2">
          <div className="flex items-center gap-4 border-b border-primary-dark/10">
            {job.companyLogo && (
              <div className="size-32">
                <img
                  src={job.companyLogo}
                  alt={job.companyName}
                  className="w-full h-full object-contain"
                />
              </div>
            )}
            <div className="h-32 flex flex-col justify-center">
              <h1 className="text-3xl font-bold pb-2 text-balance">
                {job.title}
              </h1>
              <p className="text-muted-foreground">
                {job.companyName} - {job.address}
              </p>
            </div>
          </div>

          <div className="flex items-center gap-2 border-b border-primary-dark/10 py-4">
            <div className="flex items-center gap-2 bg-neutral-100 px-6 rounded-full py-2">
              <BagIcon /> {translateContractType(job.contractType)}
            </div>
            <div className="flex items-center gap-2 bg-neutral-100 px-6 rounded-full py-2">
              {translateWorkMode(job.workMode) === 'Presencial' ? (
                <BuildingIcon />
              ) : (
                <MonitorIcon />
              )}{' '}
              {translateWorkMode(job.workMode)}
            </div>
            <div className="flex items-center gap-2 bg-neutral-100 px-6 rounded-full py-2">
              <MoneyIcon />
              {currencyFormatter(job.salary)}
            </div>
            <div className="px-6">
              Publicada em {dateStringFormatter(job.createdAt)}
            </div>
          </div>

          <div className="mt-4 grid gap-4">
            <div>
              <h2 className="text-xl font-semibold pb-2">Sobre a vaga</h2>
              <p className="">{job.description}</p>
            </div>

            <div>
              <h2 className="text-xl font-semibold pb-2">Requisitos</h2>
              <p className="whitespace-pre-line">{job.requirements}</p>
            </div>

            {job.responsibilities && (
              <div>
                <h2 className="text-xl font-semibold pb-2">
                  Responsabilidades
                </h2>
                <p className="whitespace-pre-line">{job.responsibilities}</p>
              </div>
            )}

            <div className="grid border-y border-y-primary-dark/10 py-4 grid-cols-1 md:grid-cols-2 gap-4">
              {job.courseRequired && (
                <p className="flex items-center gap-2">
                  <span className="font-bold flex items-center gap-2">
                    <GraduationIcon /> Curso exigido:
                  </span>{' '}
                  {job.courseRequired}
                </p>
              )}
              {job.graduationYearRequired && (
                <p className="flex items-center gap-2">
                  <span className="font-bold flex items-center gap-2">
                    <GraduationIcon /> Ano de graduação exigido:
                  </span>{' '}
                  {new Date(job.graduationYearRequired).getFullYear()}
                </p>
              )}
              {job.minSemester && (
                <p className="flex items-center gap-2">
                  <span className="font-bold">Semestre mínimo:</span>{' '}
                  {job.minSemester}
                </p>
              )}
              {job.website && (
                <div className="flex gap-2 items-center py-4">
                  <GlobeIcon />
                  <a
                    href={job.website}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="underline"
                  >
                    Website da empresa
                  </a>
                </div>
              )}
            </div>

            <div className="mt-6 flex items-center gap-5">
              <Button
                bgColor
                onClick={handleClick}
                disabled={isError || isFetchUser || isCompany || isApplicating}
                className="px-8 py-3 rounded-lg hover:bg-primary-dark-2/80 duration-200 disabled:bg-slate-500"
              >
                {(isError || isFetchUser) && 'Faça o login para se candidatar'}
                {isCompany && 'Somente para candidatos'}
                {isCandidate && !isError && !isApplicating && 'Candidatar-se'}
                {isCandidate && !isError && isApplicating && 'Carregando...'}
              </Button>
              <div className="text-lg font-medium text-primary-dark/80">
                Atenção: a vaga será encerrada em{' '}
                {partialDateFormatter(job.expiresAt)}
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default JobDetails;
