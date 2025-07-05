import { useForm, type SubmitHandler } from 'react-hook-form';
import { MagnifyingGlassIcon } from '../icons/MagnifyingGlassIcon';
import { MapInIcon } from '../icons/MapInIcon';
import { Button } from '../ui/Button';
import { zodResolver } from '@hookform/resolvers/zod';
import { SearchSchema, type SearchType } from '../../schemas/search';
import { useFilterStore } from '../../store/filterStore';
import { useNavigate } from 'react-router';

export const Search = () => {
  const navigate = useNavigate();

  const setTitle = useFilterStore(state => state.setTitle);
  const setLocation = useFilterStore(state => state.setLocation);

  const { register, handleSubmit } = useForm<SearchType>({
    resolver: zodResolver(SearchSchema),
    defaultValues: {
      title: '',
      location: '',
    },
  });

  const onSubmit: SubmitHandler<SearchType> = data => {
    setTitle(data.title);
    setLocation(data.location);

    navigate('/vagas');
  };

  return (
    <form
      className="mx-auto w-[80%] grid py-6 grid-cols-3 gap-3 rounded-2xl px-6 custom-shadow bg-white relative z-10"
      onSubmit={handleSubmit(onSubmit)}
    >
      <h2 className="col-span-full pb-6 pt-2 font-medium text-xl">
        Que vaga <span className="text-secundary">tech</span> você procura?
      </h2>
      <div className="relative">
        <MagnifyingGlassIcon className="absolute top-1/2 left-0 -translate-y-1/2 translate-x-2 text-primary-dark" />
        <input
          id="title"
          {...register('title')}
          className="w-full py-3 pl-10 pr-3 text-primary-dark border border-primary-dark/50 rounded-xl"
          placeholder="Cargo, palavras-chave..."
        />
      </div>
      <div className="relative">
        <MapInIcon className="absolute top-1/2 left-0 -translate-y-1/2 translate-x-2 text-primary-dark" />
        <input
          id="location"
          className="w-full py-3 pl-10 pr-3 text-primary-dark border border-primary-dark/50 rounded-xl"
          {...register('location')}
          placeholder="Localização"
        />
      </div>
      <div>
        <Button
          bgColor
          className="w-full h-full rounded-lg hover:bg-slate-600 duration-200"
        >
          Pesquisar vagas
        </Button>
      </div>
    </form>
  );
};
