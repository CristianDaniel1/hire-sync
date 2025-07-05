import { CandidateApplications } from '../components/candidates/CandidateApplications';
import { Resume } from '../components/candidates/Resume';
import { PencilIcon } from '../components/icons/PencilIcon';
import { UserIcon } from '../components/icons/UserIcon';
import { useAuth } from '../hooks/queries/useAuth';
import { translateGender } from '../utils/formatting';

const CandidateProfile = () => {
  const { userAuth: candidate } = useAuth();

  if (candidate?.type !== 'CANDIDATE') {
    return (
      <div className="text-xl text-center py-24 text-red-700 font-semibold">
        Somente para candidatos!
      </div>
    );
  }

  return (
    <div className="max-container padding-x padding-y grid gap-10 min-h-screen">
      <h1 className="text-3xl font-bold pt-6">Meu Perfil</h1>

      {candidate && (
        <section className="bg-white rounded-xl shadow grid overflow-clip">
          <div className="flex justify-between text-white  bg-primary-dark-2 items-center px-6 py-4 ">
            <h2 className="text-xl font-semibold flex items-center gap-2">
              <UserIcon />
              Informações Pessoais
            </h2>
            <button className="flex items-center font-medium gap-1 px-2">
              <PencilIcon /> Editar
            </button>
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-gray-700 p-6">
            <div>
              <strong>Nome:</strong> {candidate.name}
            </div>
            <div>
              <strong>Email:</strong> {candidate.email}
            </div>
            <div>
              <strong>CPF:</strong> {candidate.cpf}
            </div>
            {candidate.phone && (
              <div>
                <strong>Telefone:</strong> {candidate.phone}
              </div>
            )}
            <div>
              <strong>Curso:</strong> {candidate.course}
            </div>
            {candidate.currentSemester && (
              <div>
                <strong>Semestre Atual:</strong> {candidate.currentSemester}
              </div>
            )}
            {candidate.graduationYear && (
              <div>
                <strong>Ano de Graduação:</strong>{' '}
                {new Date(candidate.graduationYear).getFullYear()}
              </div>
            )}
            <div>
              <strong>Estudando atualmente?</strong>{' '}
              {candidate.currentlyStudying ? 'Sim' : 'Não'}
            </div>
            {candidate.gender && (
              <div>
                <strong>Gênero:</strong> {translateGender(candidate.gender)}
              </div>
            )}
            <div>
              <strong>Endereço:</strong> {candidate.address}
            </div>
          </div>
        </section>
      )}
      <Resume candidateId={candidate.id} />

      <CandidateApplications candidateId={candidate.id} />
    </div>
  );
};

export default CandidateProfile;
