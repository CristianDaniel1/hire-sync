import { TrashIcon } from '../components/icons/TrashIcon';
import { JobItem } from '../components/jobs/JobItem';
import { Search } from '../components/jobs/Search';
import { WorkModeFilter } from '../components/jobs/WorkModeFilter';
import { Button } from '../components/ui/Button';
import { useJobs } from '../hooks/queries/useJobs';
import { useFilterStore } from '../store/filterStore';
import img from '../assets/tech.jpg';

const Jobs = () => {
  const title = useFilterStore(state => state.title);
  const location = useFilterStore(state => state.location);
  const workMode = useFilterStore(state => state.workMode);
  const clearFilters = useFilterStore(state => state.clearFilters);
  const {
    jobs,
    isPending,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isError,
  } = useJobs({
    title: title,
    location: location,
    workMode: workMode,
  });

  return (
    <div className="min-h-screen bg-white">
      <div className="relative overflow-clip">
        <div className="pb-[2.5rem] pt-32 max-container-2">
          <Search />
        </div>
        <img
          src={img}
          alt=""
          className="w-full h-full top-0 left-0 absolute object-cover brightness-50 z-[1]"
        />
      </div>
      <section className="min-h-screen pb-20">
        <div className="flex justify-center gap-4 py-5 shadow">
          <WorkModeFilter currentWorkMode={workMode} />
          <button
            onClick={clearFilters}
            className="flex items-center gap-2 font-medium text-secundary/80 hover:text-secundary duration-200"
          >
            <TrashIcon /> Limpar filtros
          </button>
        </div>
        <ul className="grid grid-cols-3 gap-5 max-container padding-x pt-5 pb-12">
          {jobs?.length > 0 &&
            jobs.map(job => <JobItem key={job!.id} {...job!} />)}
        </ul>
        {!isPending && !isFetchingNextPage && jobs.length === 0 && (
          <div className="text-xl mx-auto max-w-[30rem] text-center py-5 px-8 bg-blue-100 font-medium text-primary-dark/80 border-2 border-primary-dark/30 rounded-md mt-4">
            Sem vagas com o filtro aplicado.
          </div>
        )}
        {hasNextPage && !isError && (
          <Button
            className="mx-auto block py-3 font-medium px-8 rounded-lg hover:bg-primary-dark-2 hover:text-white duration-200"
            onClick={() => {
              void fetchNextPage();
            }}
          >
            Ver mais vagas
          </Button>
        )}
      </section>
    </div>
  );
};

export default Jobs;
