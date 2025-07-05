import { Link } from 'react-router';
import { useApplications } from '../../hooks/queries/useApplications';
import { ResumeIcon } from '../icons/ResumeIcon';
import { partialDateFormatter, translateStatus } from '../../utils/formatting';

export const CandidateApplications = ({
  candidateId,
}: {
  candidateId: string;
}) => {
  const { applications } = useApplications({ id: candidateId });

  return (
    <section className="bg-white rounded-xl shadow overflow-hidden">
      <h2 className="text-xl font-semibold px-6 py-4 flex items-center gap-2 border">
        <ResumeIcon /> Minhas Candidaturas
      </h2>
      {applications?.length === 0 || !applications ? (
        <p className="text-primary-dark p-6">
          Você ainda não se candidatou a nenhuma vaga.
        </p>
      ) : (
        <ul>
          {applications?.map(app => (
            <li key={app.id} className="p-6 border-b">
              <div className="flex justify-between items-center">
                <div>
                  <p className="text-lg font-semibold text-primary-dark">
                    {app.jobTitle}
                  </p>
                  <p className="text-sm text-primary-dark-2">
                    {app.companyName}
                  </p>
                  <p className="text-sm text-gray-500 pt-2">
                    Status:{' '}
                    <span className="text-secundary font-medium">
                      {translateStatus(app.status)}
                    </span>
                  </p>
                  <p className="text-sm text-gray-400">
                    Candidatado em: {partialDateFormatter(app.createdAt)}
                  </p>
                </div>
                <Link
                  to={`/vagas/${app.jobId}`}
                  className="text-white bg-primary-dark-2 px-12 py-3 rounded-lg hover:bg-primary-dark duration-200"
                >
                  Ver vaga
                </Link>
              </div>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
};
