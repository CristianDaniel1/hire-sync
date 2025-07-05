import { useResume } from '../../hooks/queries/useResume';
import { PdfIcon } from '../icons/PdfIcon';
import { PencilIcon } from '../icons/PencilIcon';
import { ScrollIcon } from '../icons/ScrollIcon';

export const Resume = ({ candidateId }: { candidateId: string }) => {
  const { resume } = useResume({ id: candidateId });

  return (
    <section className="bg-white rounded-xl custom-shadow overflow-clip">
      <div className="flex justify-between items-center border px-6 py-4 ">
        <h2 className="text-xl font-semibold flex items-center gap-2 ">
          <ScrollIcon />
          Meu Currículo
        </h2>
        <button className="flex items-center gap-1 px-2 text-primary-dark/80">
          <PencilIcon /> Editar
        </button>
      </div>
      {resume ? (
        <div className="space-y-2 text-gray-700 p-6">
          <p>
            <strong>Resumo:</strong> {resume.summary || 'Não informado.'}
          </p>
          <p>
            <strong>Habilidades:</strong> {resume.skills || 'Não informado.'}
          </p>
          <p>
            <strong>Idiomas:</strong> {resume.languages || 'Não informado.'}
          </p>
          <p>
            <strong>Certificações:</strong>{' '}
            {resume.certifications || 'Não informado.'}
          </p>
          <h3 className="font-bold pt-2">Links:</h3>
          <div className="flex flex-wrap gap-6 mt-2 border py-3 px-6 w-fit">
            {resume.githubUrl && (
              <a
                className="text-primary-dark-2 underline"
                href={resume.githubUrl}
                target="_blank"
              >
                GitHub
              </a>
            )}
            {resume.portfolioUrl && (
              <a
                className="text-primary-dark-2 underline"
                href={resume.portfolioUrl}
                target="_blank"
              >
                Portfolio
              </a>
            )}
            {resume.linkedinUrl && (
              <a
                className="text-primary-dark-2 underline"
                href={resume.linkedinUrl}
                target="_blank"
              >
                LinkedIn
              </a>
            )}
            {resume.repositoryUrl && (
              <a
                className="text-primary-dark-2 underline"
                href={resume.repositoryUrl}
                target="_blank"
              >
                Repositório
              </a>
            )}
          </div>
          {resume.fileUrl && (
            <div className="pt-4">
              <a
                className="flex items-center gap-1 bg-red-500 px-8 rounded-md py-3 text-white w-fit hover:bg-red-700 duration-200"
                href={'http://localhost:8080' + resume.fileUrl}
                target="_blank"
                download
              >
                <PdfIcon />
                Ver Currículo PDF
              </a>
              <p className="text-sm text-primary-dark/60 pt-2">
                Ao se candidatar em uma vaga, o seu currículo será anexado no
                e-mail enviado a empresa!
              </p>
            </div>
          )}
        </div>
      ) : (
        <div className="text-gray-600 py-8 px-4">
          <p className="pb-4">Você ainda não criou seu currículo. </p>
          <button className="bg-primary-dark-2 rounded-lg px-8 py-2 text-white">
            Clique aqui para criar
          </button>
          .
        </div>
      )}
    </section>
  );
};
