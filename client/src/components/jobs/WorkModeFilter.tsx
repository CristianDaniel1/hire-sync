import { useFilterStore } from '../../store/filterStore';
import type { WorkMode } from '../../types';

export const WorkModeFilter = ({
  currentWorkMode,
}: {
  currentWorkMode: WorkMode | null;
}) => {
  const setWorkMode = useFilterStore(state => state.setWorkMode);

  function handleClick(workMode: WorkMode) {
    setWorkMode(workMode);
  }

  return (
    <div className="flex items-center gap-3">
      <p className="font-medium text-primary-dark">Modelo de trabalho</p>
      <ul className="flex items-center justify-around gap-3 flex-1">
        <li>
          <button
            onClick={() => handleClick('ONSITE')}
            className={`px-10 py-2 rounded-full border-2 duration-200 ${
              currentWorkMode === 'ONSITE'
                ? 'border-secundary/30 text-secundary'
                : 'hover:bg-secundary/20 border-primary-dark/20'
            }`}
          >
            Presencial
          </button>
        </li>
        <li>
          <button
            onClick={() => handleClick('HYBRID')}
            className={`px-10 py-2 rounded-full border-2 hover:bg-secundary/20 duration-200 ${
              currentWorkMode === 'HYBRID'
                ? 'border-secundary/30 text-secundary'
                : 'hover:bg-secundary/20 border-primary-dark/20'
            }`}
          >
            Híbrido
          </button>
        </li>
        <li>
          <button
            onClick={() => handleClick('REMOTE')}
            className={`px-10 py-2 rounded-full border-2 hover:bg-secundary/20 duration-200 ${
              currentWorkMode === 'REMOTE'
                ? 'border-secundary/30 text-secundary'
                : 'hover:bg-secundary/20 border-primary-dark/20'
            }`}
          >
            Remoto
          </button>
        </li>
      </ul>
    </div>
  );
};
