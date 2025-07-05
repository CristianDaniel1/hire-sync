import { create } from 'zustand';
import type { WorkMode } from '../types';

interface Filter {
  title: string | undefined;
  location: string | undefined;
  workMode: WorkMode | null;

  setTitle: (title: string | undefined) => void;
  setLocation: (location: string | undefined) => void;
  setWorkMode: (workMode: WorkMode | null) => void;
  clearFilters: () => void;
}

export const useFilterStore = create<Filter>(set => {
  return {
    title: undefined,
    location: undefined,
    workMode: null,

    setTitle: title => set({ title }),
    setLocation: location => set({ location }),
    setWorkMode: workMode => set({ workMode }),
    clearFilters: () => {
      set({ title: undefined, location: undefined, workMode: null });
    },
  };
});
